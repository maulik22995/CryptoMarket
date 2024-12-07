package com.market.crypto.data.mapper

import com.market.crypto.data.source.remote.model.MarketStatsApiModel
import com.market.crypto.ui.model.MarketStats
import com.market.crypto.ui.model.Percentage

class MarketStatsMapper {
    fun mapApiModelToModel(apiModel: MarketStatsApiModel): MarketStats {
        val marketStats = apiModel.data?.stats

        return MarketStats(
            marketCapChangePercentage24h = Percentage(marketStats?.marketCapChange)
        )
    }
}
