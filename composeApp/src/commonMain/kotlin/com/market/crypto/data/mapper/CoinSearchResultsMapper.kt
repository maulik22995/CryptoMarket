package com.market.crypto.data.mapper

import com.market.crypto.data.source.remote.model.CoinSearchResultsApiModel
import com.market.crypto.model.SearchCoin

class CoinSearchResultsMapper {

    fun mapApiModelToModel(apiModel: CoinSearchResultsApiModel): List<SearchCoin> {
        val validSearchResult = apiModel.coinsSearchResultsData?.coins
            .orEmpty()
            .filterNotNull()
            .filter { it.uuid != null }

        return validSearchResult.map { searchedCoin ->
            SearchCoin(
                id = searchedCoin.uuid!!,
                name = searchedCoin.name.orEmpty(),
                symbol = searchedCoin.symbol.orEmpty(),
                imageUrl = searchedCoin.iconUrl.orEmpty()
            )
        }
    }
}