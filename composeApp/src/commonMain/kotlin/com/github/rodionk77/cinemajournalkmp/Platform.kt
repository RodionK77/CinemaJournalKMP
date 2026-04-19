package com.github.rodionk77.cinemajournalkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun isOnline(): Boolean

// Android emulator: 10.0.2.2, iOS simulator: localhost, real device: Mac IP
expect val backendHost: String