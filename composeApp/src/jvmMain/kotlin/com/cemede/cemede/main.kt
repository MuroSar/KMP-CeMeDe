package com.cemede.cemede

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cemede.cemede.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CeMeDe",
        ) {
            App()
        }
    }
}
