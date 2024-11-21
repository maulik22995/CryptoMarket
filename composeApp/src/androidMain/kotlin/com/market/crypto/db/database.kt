package com.market.crypto.db

import android.content.Context
import androidx.room.Room
import com.market.crypto.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(ctx: Context): AppDatabase {
    val dbFile = ctx.getDatabasePath("coinMarket.db")
    return Room.databaseBuilder<AppDatabase>(ctx, dbFile.absolutePath)
//        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}