package com.cemede.cemede

import androidx.compose.ui.window.ComposeUIViewController
import com.cemede.cemede.di.initKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        },
    ) {
        App()
    }
