package com.market.crypto.domain.market

import com.market.crypto.data.repository.marketStatus.MarketStatsRepository
import com.market.crypto.ui.model.MarketStats


class GetMarketStatsUseCase(
    private val marketStatsRepository: MarketStatsRepository
) {
    suspend operator fun invoke(): com.market.crypto.common.Result<MarketStats> {
        return getMarketStats()
    }

    private suspend fun getMarketStats(): com.market.crypto.common.Result<MarketStats> {
        return marketStatsRepository.getMarketStatus()
    }
}
