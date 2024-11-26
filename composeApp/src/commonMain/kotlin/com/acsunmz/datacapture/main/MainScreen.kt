package com.acsunmz.datacapture.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.acsunmz.datacapture.core.presentation.navigation.AppNavHost


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    onBoardingCompleted: Boolean,
) {
    val windowSizeClass = calculateWindowSizeClass()
    val useNavRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact

    if (useNavRail) {
        Row {
            if (onBoardingCompleted) {
             //TODO:
            }
            AppNavHost(
                navController = navController,
                completedOnboarding = onBoardingCompleted,
            )
        }
    } else {
        Scaffold(
            content = { innerPadding ->
                AppNavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    completedOnboarding = onBoardingCompleted,
                )
            }
        )

    }
}