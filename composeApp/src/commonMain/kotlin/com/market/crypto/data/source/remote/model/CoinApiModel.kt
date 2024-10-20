package com.market.crypto.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinsApiModel(
    val data: CoinsData?
)

@Serializable
data class CoinsData(
    val coins: List<CoinApiModel>
)

@Serializable
data class CoinApiModel(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: String,
    val change: String
)