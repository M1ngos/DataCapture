package com.acsunmz.datacapture

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.acsunmz.datacapture.core.presentation.theme.DataCaptureTheme
import com.acsunmz.datacapture.core.utils.koinViewModel
import com.acsunmz.datacapture.main.MainScreen
import com.acsunmz.datacapture.main.MainViewModel
import com.acsunmz.datacapture.platform.StatusBarColors
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App(
    mainViewModel: MainViewModel = koinViewModel(),

    ) {
    val darkTheme = when (mainViewModel.appTheme.collectAsState().value) {
        1 -> true
        else -> false
    }
    val onBoardingCompleted = mainViewModel.onBoardingCompleted.collectAsState().value
    KoinContext {
        DataCaptureTheme(
            useDarkTheme = darkTheme,
            ) {
            val navController = rememberNavController()
            StatusBarColors(
                statusBarColor = MaterialTheme.colorScheme.background,
                navBarColor = MaterialTheme.colorScheme.background,
            )
            when (onBoardingCompleted) {
                is MainViewModel.OnBoardingState.Success -> {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        MainScreen(
                            onBoardingCompleted = onBoardingCompleted.completed,
                            navController = navController,
                        )
                    }
                }

                else -> {}
            }
        }
    }
}