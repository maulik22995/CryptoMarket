package com.market.crypto.di

import androidx.room.Room
import com.market.crypto.data.db.AppDatabase
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformModule: Module = module {
    single { Darwin.create() }
    single<AppDatabase> { getDatabaseBuilder() }
}


fun getDatabaseBuilder():AppDatabase {
    val dbFilePath = documentDirectory() + "/coinMarket.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    ).build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
  return requireNotNull(documentDirectory?.path)
}
