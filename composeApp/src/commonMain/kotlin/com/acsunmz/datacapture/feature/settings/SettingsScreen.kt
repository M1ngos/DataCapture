package com.acsunmz.datacapture.feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.acsunmz.datacapture.core.domain.repository.settings.SettingsRepository
import com.acsunmz.datacapture.core.presentation.components.TopAppBar
import com.acsunmz.datacapture.core.utils.koinViewModel
import com.acsunmz.datacapture.platform.StatusBarColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val darkTheme = when (viewModel.appTheme.collectAsState().value) {
        1 -> true
        else -> false
    }
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    SettingsScreenContent(
        darkTheme = darkTheme,
        onDarkThemeChange = { themeValue ->
            viewModel.setAppTheme(if (themeValue) 1 else 0)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                hasBackNavigation = false,
            ) {
                Text(text = "Settings")
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                ThemeSetting(
                    darkTheme = darkTheme,
                    onDarkThemeChange = onDarkThemeChange,
                )
            }
        }
    }
}

@Composable
fun ThemeSetting(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    AutoStartSession(
        title = "App Theme (${
            if (darkTheme) {
                "Dark"
            } else {
                "Light"
            }
        })",
        checked = darkTheme,
        onCheckedChange = {
            onDarkThemeChange(it)
        },
    )
}

@Composable
fun AutoStartSession(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
fun SettingCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onExpand: () -> Unit,
    expanded: Boolean,
) {
    Card(
        modifier = modifier,
        onClick = {
            onExpand()
        },
    ) {
        Column(
            modifier = modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                IconButton(onClick = { onExpand() }) {
                    Icon(
                        imageVector = if (expanded) {
                            Icons.Rounded.KeyboardArrowUp
                        } else {
                            Icons.Rounded.KeyboardArrowDown
                        },
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
        }
    }
}

