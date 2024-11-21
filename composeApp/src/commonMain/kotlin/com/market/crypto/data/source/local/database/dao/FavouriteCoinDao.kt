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
    suspend fun getFavouriteCoins(): List<FavouriteCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCoins(favouriteCoins: List<FavouriteCoin>)

    @Query("DELETE FROM FavouriteCoin")
    suspend fun deleteAllFavouriteCoins()

    @Transaction
    suspend fun updateFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        deleteAllFavouriteCoins()
        insertFavouriteCoins(favouriteCoins)
    }
}
