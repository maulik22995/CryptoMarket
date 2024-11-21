package com.market.crypto.di

import com.market.crypto.data.db.AppDatabase
import com.market.crypto.db.getDatabaseBuilder
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { OkHttp.create() }
    single<AppDatabase> { getDatabaseBuilder(get()) }
}