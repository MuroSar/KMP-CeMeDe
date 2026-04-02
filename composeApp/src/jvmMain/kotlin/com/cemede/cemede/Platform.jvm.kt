package com.cemede.cemede

import java.awt.Desktop
import java.net.URI

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override fun openUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URI(url))
            }
        }
    }
}

actual fun getPlatform(): Platform = JVMPlatform()
