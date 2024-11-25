package com.acsunmz.datacapture

import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.acsunmz.datacapture.core.presentation.theme.DataCaptureTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    DataCaptureTheme {
        val navController = rememberNavController()

    }
}