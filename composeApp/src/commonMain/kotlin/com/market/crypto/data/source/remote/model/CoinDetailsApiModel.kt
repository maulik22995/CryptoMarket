package com.market.crypto.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailsApiModel(
    val data: CoinDetailsDataHolder?
)

@Serializable
data class CoinDetailsDataHolder(
    val coin: CoinDetailsData?
)

@Serializable
data class CoinDetailsData(
    val uuid: String?,
    val name: String?,
    val symbol: String?,
    val iconUrl: String?,
    val price: String?,
    val marketCap: String?,
    val rank: String?,
    @SerialName("24hVolume")
    val volume24h: String?,
    val supply: Supply?,
    val allTimeHigh: AllTimeHigh?,
    val listedAt: Long?
)

@Serializable
data class Supply(
    val circulating: String?
)

@Serializable
data class AllTimeHigh(
    val price: String?,
    val timestamp: Long?
)
