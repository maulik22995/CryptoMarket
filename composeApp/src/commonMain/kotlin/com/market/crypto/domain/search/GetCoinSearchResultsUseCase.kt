package com.market.crypto.domain.search

import com.market.crypto.common.Result
import com.market.crypto.data.repository.search.CoinSearchResultsRepository
import com.market.crypto.model.SearchCoin
import kotlinx.coroutines.flow.Flow

class GetCoinSearchResultsUseCase(
    private val coinSearchResultsRepository: CoinSearchResultsRepository
) {
    suspend fun invoke(searchQuery : String) : Flow<Result<List<SearchCoin>>> {
        return coinSearchResultsRepository.getCoinSearchResults(searchQuery = searchQuery)
    }
}