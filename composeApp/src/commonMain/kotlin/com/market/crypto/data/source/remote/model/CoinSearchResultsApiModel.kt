package com.market.crypto.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinSearchResultsApiModel(
    @SerialName("data")
    val coinsSearchResultsData: CoinSearchResultsData?
)

@Serializable
data class CoinSearchResultsData(
    val coins: List<CoinSearchResult?>?
)

@Serializable
data class CoinSearchResult(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val iconUrl: String?
)
