@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package com.market.crypto.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.market.crypto.data.source.local.database.dao.CoinDao
import com.market.crypto.data.source.local.database.dao.FavouriteCoinDao
import com.market.crypto.data.source.local.database.dao.FavouriteCoinIdDao
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import com.market.crypto.data.source.local.database.model.FavouriteCoinId

@Database(entities = [Coin::class, FavouriteCoin::class, FavouriteCoinId::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun coinDao(): CoinDao
    abstract fun favouriteCoinDao(): FavouriteCoinDao
    abstract fun favouriteCoinIdDao(): FavouriteCoinIdDao
    override fun clearAllTables() {
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

interface DB {
    fun clearAllTables(): Unit {}
}