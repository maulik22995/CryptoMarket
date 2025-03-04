package com.market.crypto.data.mapper


import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.model.CoinChartApiModel
import com.market.crypto.model.CoinChart
import kotlinx.collections.immutable.toPersistentList

class CoinChartMapper {
    fun mapApiModelToModel(apiModel: CoinChartApiModel, currency: Currency): CoinChart {
        val validPrices = apiModel.coinChartData?.pastPrices
            .orEmpty()
            .mapNotNull { pastPrice ->
                pastPrice?.amount?.toDouble()
            }
            .filter { price -> price >= 0 }
            .reversed()

        val minPrice = when {
            validPrices.isNotEmpty() -> validPrices.minOrNull().toString()
            else -> null
        }

        val maxPrice = when {
            validPrices.isNotEmpty() -> validPrices.maxOrNull().toString()
            else -> null
        }

        return CoinChart(
            prices = validPrices.toPersistentList(),
            minPrice = minPrice.orEmpty(),
            maxPrice = maxPrice.orEmpty(),
            periodPriceChangePercentage = apiModel.coinChartData?.priceChangePercentage.orEmpty()
        )
    }
}
