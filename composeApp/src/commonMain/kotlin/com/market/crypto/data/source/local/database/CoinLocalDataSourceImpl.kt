package com.market.crypto.data.source.local.database

import com.market.crypto.data.db.AppDatabase
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import com.market.crypto.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        return flow { appDatabase.favouriteCoinDao().getFavouriteCoins() }
    }

    override suspend fun updateFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        appDatabase.favouriteCoinDao().updateFavouriteCoins(favouriteCoins)
    }

    override fun getFavouriteCoinIds(): Flow<List<FavouriteCoinId>> {
        return flow { appDatabase.favouriteCoinIdDao().getFavouriteCoinIds() }
    }

    override fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Boolean> {
        return flow {
            appDatabase.favouriteCoinIdDao().isCoinFavourite(coinId = favouriteCoinId.id)
        }
    }

    override suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId) {
        val isCoinFavourite =
            appDatabase.favouriteCoinIdDao().isCoinFavouriteOneShot(favouriteCoinId.id)

        if (isCoinFavourite) {
            appDatabase.favouriteCoinIdDao().delete(favouriteCoinId)
        } else {
            appDatabase.favouriteCoinIdDao().insert(favouriteCoinId)
        }
    }
}