package com.market.crypto.data.repository.details

import com.market.crypto.common.Result
import com.market.crypto.data.mapper.CoinDetailsMapper
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.model.CoinDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoinDetailsRepositoryImpl(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinDetailsMapper: CoinDetailsMapper
) : CoinDetailsRepository {
    override fun getCoinDetails(coinId: String, currency: Currency): Flow<Result<CoinDetails>> =
        flow {
            try {
                val details = coinNetworkDataSource.getCoinDetails(coinId, currency)
                println("details>>" + details.toString())
                val coinDetails = coinDetailsMapper.mapApiModelToModel(details, currency)
                emit(Result.Success(coinDetails))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
}