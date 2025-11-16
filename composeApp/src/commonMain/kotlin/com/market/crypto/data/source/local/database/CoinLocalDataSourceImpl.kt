package com.market.crypto.data.source.local.database

import com.market.crypto.data.db.AppDatabase
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CoinLocalDataSourceImpl(
    private val appDatabase: AppDatabase,
) : CoinLocalDataSource {
    override fun getCoins(): Flow<List<Coin>> {
        return appDatabase.coinDao().getCoins()
    }

    override suspend fun updateCoins(coins: List<Coin>) {
        appDatabase.coinDao().updateCoins(coins)
    }

    override fun getFavouriteCoins(): Flow<List<FavouriteCoin>> {
        return flow { 
            emit(withContext(Dispatchers.IO) {
                appDatabase.favouriteCoinDao().getFavouriteCoins()
            })
        }
    }

    override suspend fun insertFavouriteCoin(favouriteCoin: FavouriteCoin) {
        withContext(Dispatchers.IO) {
            appDatabase.favouriteCoinDao().insertFavouriteCoin(favouriteCoin)
        }
    }

    override fun isCoinFavourite(favouriteCoinId: String): Flow<Boolean> {
        return flow {
            emit(withContext(Dispatchers.IO) {
                appDatabase.favouriteCoinDao().isCoinFavourite(favouriteCoinId) > 0
            })
        }
    }

    override suspend fun toggleIsCoinFavourite(favouriteCoin: FavouriteCoin) {
        withContext(Dispatchers.IO) {
            val dao = appDatabase.favouriteCoinDao()
            val isFav = dao.isCoinFavourite(favouriteCoin.id) > 0

            if (isFav) {
                dao.deleteFavouriteCoin(favouriteCoin.id)
            } else {
                dao.insertFavouriteCoin(favouriteCoin)
            }
        }
    }
}