package com.cemede.cemede

import platform.UIKit.UIDevice
import platform.UIKit.UIApplication
import platform.Foundation.NSURL

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any?>()) { success ->
                if (!success) {
                    println("Error: No se pudo abrir la URL $url")
                }
            }
        }
    }
}

actual fun getPlatform(): Platform = IOSPlatform()
