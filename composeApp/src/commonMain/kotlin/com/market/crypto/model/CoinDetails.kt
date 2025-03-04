package com.market.crypto.model

data class CoinDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: String,
    val marketCap: String,
    val marketCapRank: String,
    val volume24h: String,
    val circulatingSupply: String,
    val allTimeHigh: String,
    val allTimeHighDate: Long,
    val listedDate: Long
)
