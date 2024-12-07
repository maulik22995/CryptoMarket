package com.market.crypto.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketStatsApiModel(
    val data: MarketStatsDataHolder?
)

@Serializable
data class MarketStatsDataHolder(
    val stats: MarketStatsData?
)

@Serializable
data class MarketStatsData(
    val marketCapChange: String?
)