package com.market.crypto.data.repository.marketStatus

import com.market.crypto.common.Result
import com.market.crypto.data.mapper.MarketStatsMapper
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.ui.model.MarketStats

class MarketStatsRepositoryImpl(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val marketStatsMapper: MarketStatsMapper
) : MarketStatsRepository {
    override suspend fun getMarketStatus(): Result<MarketStats> {
        return try {
            val response = coinNetworkDataSource.getMarketStatus()
            val marketStatus = marketStatsMapper.mapApiModelToModel(response)
            println("marketStatus >>" + marketStatus)
            Result.Success(marketStatus)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

}