package com.market.crypto.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCoinDao {
    @Query("SELECT * FROM FavouriteCoin")
    fun getFavouriteCoins(): Flow<List<FavouriteCoin>>

    @Query("DELETE FROM FavouriteCoin")
    suspend fun deleteAllFavouriteCoins()

    // Insert with ignore strategy to avoid crash on duplicate
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteCoin(coin: FavouriteCoin)

    // Delete a single coin by ID
    @Query("DELETE FROM FavouriteCoin WHERE id = :coinId")
    suspend fun deleteFavouriteCoin(coinId: String)

    // Check if coinId exists
    @Query("SELECT COUNT(*) FROM FavouriteCoin WHERE id = :coinId")
    suspend fun isCoinFavourite(coinId: String): Int
}
