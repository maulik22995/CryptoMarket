package com.market.crypto.common

actual fun coinrankingApiKey(): String = System.getenv("COINRANKING_API_KEY") ?: ""
