package com.market.crypto.data.source.remote

import com.market.crypto.common.Constants.BASE_URL
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.local.model.getOrderBy
import com.market.crypto.data.source.local.model.getOrderDirection
import com.market.crypto.data.source.local.model.toCurrencyUUID
import com.market.crypto.data.source.remote.model.CoinChartApiModel
import com.market.crypto.data.source.remote.model.CoinDetailsApiModel
import com.market.crypto.data.source.remote.model.CoinsApiModel
import com.market.crypto.data.source.remote.model.MarketStatsApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CoinNetworkDataSourceImpl(private val httpClient: HttpClient) : CoinNetworkDataSource {
    override suspend fun getCoins(coinSort: CoinSort, currency: Currency): CoinsApiModel =
        withContext(Dispatchers.IO) {
            return@withContext httpClient.get("${BASE_URL}coins") {
                parameter("referenceCurrencyUuid", currency.toCurrencyUUID())
                parameter("orderBy", coinSort.getOrderBy())
                parameter("timePeriod", "24h")
                parameter("orderDirection", coinSort.getOrderDirection())
                parameter("limit", "100")
            }.body<CoinsApiModel>()

        }

    override suspend fun getMarketStatus(): MarketStatsApiModel = withContext(Dispatchers.IO) {
        return@withContext httpClient.get("${BASE_URL}stats/coins").body<MarketStatsApiModel>()
    }

    override suspend fun getCoinDetails(coinId: String, currency: Currency): CoinDetailsApiModel =
        withContext(Dispatchers.IO) {
            val response = httpClient.get("${BASE_URL}coin/${coinId}"){
                parameter("referenceCurrencyUuid",currency.toCurrencyUUID())
            }.body<CoinDetailsApiModel>()
            println("response >> ${response}")
            return@withContext response
        }

    override suspend fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): CoinChartApiModel = withContext(Dispatchers.IO){
        return@withContext httpClient.get("${BASE_URL}coin/${coinId}/history"){
            parameter("referenceCurrencyUuid",currency.toCurrencyUUID())
            parameter("timePeriod",chartPeriod)
        }.body()
    }
}