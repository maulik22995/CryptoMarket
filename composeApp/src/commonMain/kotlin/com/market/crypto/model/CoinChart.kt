package com.market.crypto.model

import kotlinx.collections.immutable.ImmutableList

data class CoinChart(
    val prices: ImmutableList<Double>,
    val minPrice: String,
    val maxPrice: String,
    val periodPriceChangePercentage: String
)
