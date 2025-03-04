package com.market.crypto.data.repository.chart

import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.model.CoinChart
import kotlinx.coroutines.flow.Flow

interface CoinChartRepository {
    fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<Result<CoinChart>>
}
