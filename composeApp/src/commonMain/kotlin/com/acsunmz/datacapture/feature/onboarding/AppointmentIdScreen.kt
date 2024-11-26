package com.acsunmz.datacapture.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acsunmz.datacapture.core.presentation.navigation.Destinations
import com.acsunmz.datacapture.core.utils.UiEvents
import com.acsunmz.datacapture.core.utils.koinViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun AppointmentIdScreen(
    navController: NavController,
    ) {
    val viewModel = koinViewModel<OnboardingViewModel>()
    val appointmentId = viewModel.appointmentId.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is UiEvents.Navigation -> {
                        navController.popBackStack()
                        navController.navigate(Destinations.CaptureImageScreen)
                    }

                    else -> {}
                }
            }
        }
    }

    AppointmentIdScreenContent(
        appointmentId = appointmentId,
        typeWriterTextParts = viewModel.typeWriterTextParts,
        onAppointmentIdChange = {
            viewModel.setAppointmentId(it)
        },
        onClickContinue = {
            keyboardController?.hide()
            viewModel.saveAppointmentId()
        },
    )



}

@Composable
fun AppointmentIdScreenContent(
    appointmentId: Int,
    typeWriterTextParts: List<String>,
    onAppointmentIdChange: (Int) -> Unit,
    onClickContinue: () -> Unit
 ) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        item {
            TypewriterText(
                baseText = "This prototype aims to ",
                parts = typeWriterTextParts,
            )
        }

        item {
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = "Please input your appointment Id:",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp,
                ),
            )
        }

        item {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            AppointmentIdField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                appointmentId = appointmentId,
                onIdChange = {
                    onAppointmentIdChange(it)
                },
                onClickDone = {
                    onClickContinue()
                },
            )
        }

        item {
            AnimatedVisibility(
                visible = appointmentId.toString().length >= 5
            ) {
                Column {
                    Spacer(modifier = Modifier.height(56.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = MaterialTheme.shapes.medium,
                        onClick = onClickContinue,
                    ) {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentIdField(
    modifier: Modifier,
    appointmentId: Int,
    onIdChange: (Int) -> Unit,
    onClickDone: () -> Unit
) {
    var text by remember { mutableStateOf(appointmentId.toString()) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { newValue ->
            // Ensure the value is numeric and update the appointmentId if valid
            newValue.toIntOrNull()?.let {
                text = newValue
                onIdChange(it)
            }
        },
        label = {
            Text("Enter Appointment ID")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { onClickDone() }
        ),
        isError = text.toIntOrNull() == null && text.isNotEmpty(),
        supportingText = {
            if (text.toIntOrNull() == null && text.isNotEmpty()) {
                Text(
                    text = "Please enter a valid number",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
                )
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}


@Composable
private fun TypewriterText(modifier: Modifier = Modifier, baseText: String, parts: List<String>) {
    var partIndex by remember { mutableStateOf(0) }
    var partText by remember { mutableStateOf("") }
    val textToDisplay = "$baseText $partText"
    Text(
        modifier = modifier,
        text = textToDisplay,
        style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            letterSpacing = -(1.6).sp,
            lineHeight = 32.sp,
        ),
    )

    LaunchedEffect(key1 = parts) {
        while (partIndex <= parts.size) {
            val part = parts[partIndex]

            part.forEachIndexed { charIndex, _ ->
                partText = part.substring(startIndex = 0, endIndex = charIndex + 1)
                delay(100)
            }

            delay(1500)

            part.forEachIndexed { charIndex, _ ->
                partText = part
                    .substring(startIndex = 0, endIndex = part.length - (charIndex + 1))
                delay(30)
            }

            delay(500)

            partIndex = (partIndex + 1) % parts.size
        }
    }
}



