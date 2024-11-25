package com.acsunmz.datacapture

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform