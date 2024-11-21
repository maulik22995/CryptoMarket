package com.market.crypto.data.mapper

import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.remote.model.CoinsApiModel


class CoinMapper {
    fun mapApiModelToModel(apiModel: CoinsApiModel): List<Coin> {
        val validCoins = apiModel.data?.coins
            .orEmpty()
            .filterNotNull()

        return validCoins.map { coinApiModel ->
            Coin(
                id = coinApiModel.uuid,
                name = coinApiModel.name.orEmpty(),
                symbol = coinApiModel.symbol.orEmpty(),
                imageUrl = coinApiModel.iconUrl.orEmpty(),
                price = coinApiModel.price,
                change = coinApiModel.change,
            )
        }
    }
}
