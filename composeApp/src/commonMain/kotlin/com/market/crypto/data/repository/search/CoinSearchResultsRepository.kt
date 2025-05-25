package com.market.crypto.data.repository.search

import com.market.crypto.common.Result
import com.market.crypto.model.SearchCoin
import kotlinx.coroutines.flow.Flow

interface CoinSearchResultsRepository {
    suspend fun getCoinSearchResults(searchQuery: String): Flow<Result<List<SearchCoin>>>
}