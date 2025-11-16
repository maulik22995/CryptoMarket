package com.market.crypto.data.repository.coin

import com.market.crypto.common.Result
import com.market.crypto.data.mapper.CoinMapper
import com.market.crypto.data.source.local.database.CoinLocalDataSource
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class CoinRepositoryImpl(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
    private val mapper: CoinMapper
) :
    CoinRepository {
    override suspend fun getRemoteCoins(
        coinSort: CoinSort, currency: Currency
    ): Result<List<Coin>> = withContext(Dispatchers.IO) {
        try {
            val response = coinNetworkDataSource.getCoins(coinSort, currency)
            val coins = mapper.mapApiModelToModel(response)
            println( "Coins >>"+ coins)
            Result.Success(coins)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getCachedCoins(): Flow<List<Coin>> {
       return coinLocalDataSource.getCoins()
    }

    override suspend fun updateCachedCoins(coins: List<Coin>) {
        return coinLocalDataSource.updateCoins(coins)
    }

    override fun getFavouriteCoins() = coinLocalDataSource.getFavouriteCoins()
    override suspend fun insertFavouriteCoin(favouriteCoin: FavouriteCoin) {
        coinLocalDataSource.insertFavouriteCoin(favouriteCoin)
    }

    override fun isCoinFavourite(favouriteCoinId: String) =
        coinLocalDataSource.isCoinFavourite(favouriteCoinId)

    override suspend fun toggleIsCoinFavourite(favouriteCoin: FavouriteCoin) {
        coinLocalDataSource.toggleIsCoinFavourite(favouriteCoin)
    }
}