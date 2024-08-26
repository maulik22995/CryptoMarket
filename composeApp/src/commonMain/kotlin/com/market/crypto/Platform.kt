package com.market.crypto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform