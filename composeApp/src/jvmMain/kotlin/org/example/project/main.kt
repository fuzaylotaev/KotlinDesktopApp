package org.example.project

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.WindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DesktopApp",
        state = WindowState(width = 1600.dp, height = 800.dp),
        resizable = false
    ) {
        App()
    }
}