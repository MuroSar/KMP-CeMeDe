package com.cemede.cemede

interface Platform {
    val name: String
    fun openUrl(url: String)
}

expect fun getPlatform(): Platform
