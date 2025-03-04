package com.market.crypto.data.repository.chart

import com.market.crypto.common.Result
import com.market.crypto.data.mapper.CoinChartMapper
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.model.CoinChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoinChartRepositoryImpl(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val mapper: CoinChartMapper
) : CoinChartRepository {
    override fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<Result<CoinChart>> = flow {
        try {
            val chartResponse = coinNetworkDataSource.getCoinChart(coinId, chartPeriod, currency)
            val chartData = mapper.mapApiModelToModel(chartResponse, currency)
            emit(Result.Success(chartData))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}