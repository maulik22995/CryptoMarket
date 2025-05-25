package com.market.crypto.data.repository.search

import com.market.crypto.common.Result
import com.market.crypto.data.mapper.CoinSearchResultsMapper
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.model.SearchCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoinSearchResultsRepositoryImpl(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinSearchResultsMapper: CoinSearchResultsMapper,
) : CoinSearchResultsRepository {
    override suspend fun getCoinSearchResults(searchQuery: String): Flow<Result<List<SearchCoin>>> =
        flow {
            try {
                val response = coinNetworkDataSource.getCoinSearchResults(searchQuery)
                println("Searched $response")
                val searchCoin = coinSearchResultsMapper.mapApiModelToModel(response)
                println("Searched $response")
                emit(com.market.crypto.common.Result.Success(searchCoin))
            }catch (e : Exception){
                e.printStackTrace()
                emit(com.market.crypto.common.Result.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)

}