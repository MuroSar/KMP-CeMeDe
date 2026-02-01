package com.cemede.cemede

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform