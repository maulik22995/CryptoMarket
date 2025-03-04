package com.market.crypto.domain.details

import com.market.crypto.common.Result
import com.market.crypto.data.repository.chart.CoinChartRepository
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.model.CoinChart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class GetCoinChartUseCase(
    private val coinChartRepository: CoinChartRepository,
) {
    operator fun invoke(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<Result<CoinChart>> {
        return getCoinChart(coinId = coinId, chartPeriod = chartPeriod, currency)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<com.market.crypto.common.Result<CoinChart>> {
        return coinChartRepository.getCoinChart(
            coinId = coinId,
            chartPeriod = chartPeriod,
            currency = currency
        )

    }
}
