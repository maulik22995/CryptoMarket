package com.market.crypto.common

/**
 * Coinranking API key. Provided per platform; never commit the key to the repo.
 * - Android: set in `local.properties` as `COINRANKING_API_KEY=your_key`
 * - iOS: set env var `COINRANKING_API_KEY` in Xcode scheme or edit actual in iosMain
 * - Desktop: set env var `COINRANKING_API_KEY`
 */
expect fun coinrankingApiKey(): String
