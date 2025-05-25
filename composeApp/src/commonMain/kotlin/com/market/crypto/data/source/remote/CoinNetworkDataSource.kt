package com.market.crypto.data.source.remote

import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.model.CoinChartApiModel
import com.market.crypto.data.source.remote.model.CoinDetailsApiModel
import com.market.crypto.data.source.remote.model.CoinSearchResultsApiModel
import com.market.crypto.data.source.remote.model.CoinsApiModel
import com.market.crypto.data.source.remote.model.MarketStatsApiModel

interface CoinNetworkDataSource {
    suspend fun getCoins(coinSort: CoinSort, currency: Currency): CoinsApiModel

    suspend fun getMarketStatus(): MarketStatsApiModel

    suspend fun getCoinDetails(coinId: String, currency: Currency) : CoinDetailsApiModel

    suspend fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): CoinChartApiModel

    suspend fun getCoinSearchResults(searchQuery: String): CoinSearchResultsApiModel

}