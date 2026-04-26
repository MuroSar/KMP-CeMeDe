package com.cemede.cemede

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

class AndroidPlatform(
    private val context: Context,
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val appVersion: String
        get() =
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName ?: "Unknown"
            } catch (e: PackageManager.NameNotFoundException) {
                "Unknown"
            }

    override fun openUrl(url: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        context.startActivity(intent)
    }
}

lateinit var appContext: Context

actual fun getPlatform(): Platform = AndroidPlatform(appContext)
