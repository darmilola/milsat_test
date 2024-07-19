package com.application.milsat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform