package org.hobwelt

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.hobwelt.Screen.AuthScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        //
        // Entry Point of app
        //
        AuthScreen()

    }
}