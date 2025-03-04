package com.market.crypto.data.mapper

import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.model.CoinDetailsApiModel
import com.market.crypto.model.CoinDetails


class CoinDetailsMapper {


    fun mapApiModelToModel(apiModel: CoinDetailsApiModel, currency: Currency): CoinDetails {
        val coinDetails = apiModel.data?.coin

        return CoinDetails(
            id = coinDetails?.uuid.orEmpty(),
            name = coinDetails?.name.orEmpty(),
            symbol = coinDetails?.symbol.orEmpty(),
            imageUrl = coinDetails?.iconUrl.orEmpty(),
            currentPrice = coinDetails?.price.orEmpty(),
            marketCap = coinDetails?.marketCap.orEmpty(),
            marketCapRank = coinDetails?.rank.orEmpty(),
            volume24h = coinDetails?.volume24h.orEmpty(),
            circulatingSupply = coinDetails?.supply?.circulating.orEmpty(),
            // API only supports ATH in USD
            allTimeHigh = coinDetails?.allTimeHigh?.price.orEmpty(),
            allTimeHighDate = coinDetails?.allTimeHigh?.timestamp ?: 0L,
            listedDate = coinDetails?.listedAt ?: 0L
        )
    }
}
