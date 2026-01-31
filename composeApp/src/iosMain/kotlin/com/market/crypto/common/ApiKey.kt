package com.market.crypto.common

import platform.Foundation.NSProcessInfo

actual fun coinrankingApiKey(): String =
    (NSProcessInfo.processInfo.environment["COINRANKING_API_KEY"] as? String) ?: ""
