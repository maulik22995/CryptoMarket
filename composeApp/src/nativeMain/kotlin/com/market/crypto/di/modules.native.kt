package com.market.crypto.di

import androidx.room.Room
import com.market.crypto.data.db.AppDatabase
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformModule: Module = module {
    single { Darwin.create() }
    single<AppDatabase> { getDatabaseBuilder() }
}

fun getDatabaseBuilder(): AppDatabase {
    val dbFile = "${NSHomeDirectory()}/coinMarket.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    ).build()
}