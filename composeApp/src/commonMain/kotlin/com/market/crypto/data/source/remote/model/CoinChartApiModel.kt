package com.market.crypto.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CoinChartApiModel(
    @SerialName("data")
    val coinChartData: CoinChartData?
)

@Serializable
data class CoinChartData(
    @SerialName("change")
    val priceChangePercentage: String?,
    @SerialName("history")
    val pastPrices: List<PastPrice?>?
)

@Serializable
data class PastPrice(
    @SerialName("price")
    val amount: String?
)
