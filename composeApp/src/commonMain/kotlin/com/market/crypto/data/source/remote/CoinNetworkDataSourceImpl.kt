package com.market.crypto.data.source.remote

import com.market.crypto.common.Constants.BASE_URL
import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.local.model.getOrderBy
import com.market.crypto.data.source.local.model.getOrderDirection
import com.market.crypto.data.source.local.model.toCurrencyUUID
import com.market.crypto.data.source.remote.model.CoinsApiModel
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
            return@withContext httpClient.get("$BASE_URL/coins") {
                parameter("referenceCurrencyUuid", currency.toCurrencyUUID())
                parameter("orderBy", coinSort.getOrderBy())
                parameter("timePeriod", "24h")
                parameter("orderDirection", coinSort.getOrderDirection())
                parameter("limit", "100")
            }.body<CoinsApiModel>()

        }
}