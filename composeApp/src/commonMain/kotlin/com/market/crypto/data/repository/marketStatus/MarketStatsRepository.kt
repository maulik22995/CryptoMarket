package com.market.crypto.data.repository.marketStatus

import com.market.crypto.common.Result
import com.market.crypto.ui.model.MarketStats

interface MarketStatsRepository {
    suspend fun getMarketStatus(): Result<MarketStats>
}