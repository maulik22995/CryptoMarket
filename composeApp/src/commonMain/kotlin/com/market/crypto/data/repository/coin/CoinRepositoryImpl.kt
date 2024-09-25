package com.market.crypto.data.repository.coin

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
import io.ktor.http.HeadersBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CoinRepositoryImpl(private val httpClient: HttpClient) : CoinRepository {
    override suspend fun getRemoteCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Result<CoinsApiModel> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get("$BASE_URL/coins") {
                parameter("referenceCurrencyUuid", currency.toCurrencyUUID())
                parameter("orderBy", coinSort.getOrderBy())
                parameter("timePeriod", "24h")
                parameter("orderDirection", coinSort.getOrderDirection())
                parameter("limit", "100")
            }.body<CoinsApiModel>()
            println("getRemoteCoins>> $response")
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}