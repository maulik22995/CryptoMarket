package com.market.crypto.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.market.crypto.data.source.local.database.model.FavouriteCoinId

@Dao
interface FavouriteCoinIdDao {
    @Query("SELECT * FROM FavouriteCoinId")
    suspend fun getFavouriteCoinIds(): List<FavouriteCoinId>

    @Query("SELECT COUNT(1) FROM FavouriteCoinId WHERE id = :coinId")
    suspend fun isCoinFavourite(coinId: String): Boolean

    @Query("SELECT COUNT(1) FROM FavouriteCoinId WHERE id = :coinId")
    suspend fun isCoinFavouriteOneShot(coinId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouriteCoinId: FavouriteCoinId)

    @Delete
    suspend fun delete(favouriteCoinId: FavouriteCoinId)
}
