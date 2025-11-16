package com.market.crypto.data.source.local.database

import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow

interface CoinLocalDataSource {
    fun getCoins(): Flow<List<Coin>>
    suspend fun updateCoins(coins: List<Coin>)
    fun getFavouriteCoins(): Flow<List<FavouriteCoin>>

    suspend fun insertFavouriteCoin(favouriteCoin: FavouriteCoin)
    fun isCoinFavourite(favouriteCoinId: String): Flow<Boolean>
    suspend fun toggleIsCoinFavourite(favouriteCoin: FavouriteCoin)
}