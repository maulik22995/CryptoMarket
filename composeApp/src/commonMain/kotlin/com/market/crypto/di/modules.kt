package com.market.crypto.di

import com.market.crypto.common.Constants
import com.market.crypto.data.mapper.CoinMapper
import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.repository.coin.CoinRepositoryImpl
import com.market.crypto.data.source.local.database.CoinLocalDataSource
import com.market.crypto.data.source.local.database.CoinLocalDataSourceImpl
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.data.source.remote.CoinNetworkDataSourceImpl
import com.market.crypto.domain.market.GetCoinUseCase
import com.market.crypto.domain.market.UpdateCachedCoinsUseCase
import com.market.crypto.ui.screens.market.MarketViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HeadersBuilder
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(
        networkModule,
        repositoryModule,
        dataSourceModule,
        useCasesModules,
        mapperModule,
        viewModels,
        platformModule
    )
}

expect val platformModule: Module

val viewModels = module {
    viewModelOf(::MarketViewModel)
}

val repositoryModule = module {
    singleOf(::CoinRepositoryImpl).bind<CoinRepository>()
}

val mapperModule = module {
    single { CoinMapper() }
}

val dataSourceModule = module {
    single { CoinNetworkDataSourceImpl(get()) }.bind<CoinNetworkDataSource>()
    single { CoinLocalDataSourceImpl(get()) }.bind<CoinLocalDataSource>()
}

val useCasesModules = module {
    single { GetCoinUseCase(get()) }
    single { UpdateCachedCoinsUseCase(get()) }
}

val networkModule = module {
    singleOf(::createHttpClient)
}

fun createHttpClient(): HttpClient {
    return HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            headers {
                HeadersBuilder()["x-access-toke"] = Constants.API_KEY
            }
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}