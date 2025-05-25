package com.market.crypto.di

import com.market.crypto.common.Constants
import com.market.crypto.data.mapper.CoinChartMapper
import com.market.crypto.data.mapper.CoinDetailsMapper
import com.market.crypto.data.mapper.CoinMapper
import com.market.crypto.data.mapper.CoinSearchResultsMapper
import com.market.crypto.data.mapper.MarketStatsMapper
import com.market.crypto.data.repository.chart.CoinChartRepository
import com.market.crypto.data.repository.chart.CoinChartRepositoryImpl
import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.repository.coin.CoinRepositoryImpl
import com.market.crypto.data.repository.details.CoinDetailsRepository
import com.market.crypto.data.repository.details.CoinDetailsRepositoryImpl
import com.market.crypto.data.repository.marketStatus.MarketStatsRepository
import com.market.crypto.data.repository.marketStatus.MarketStatsRepositoryImpl
import com.market.crypto.data.repository.search.CoinSearchResultsRepository
import com.market.crypto.data.repository.search.CoinSearchResultsRepositoryImpl
import com.market.crypto.data.source.local.database.CoinLocalDataSource
import com.market.crypto.data.source.local.database.CoinLocalDataSourceImpl
import com.market.crypto.data.source.remote.CoinNetworkDataSource
import com.market.crypto.data.source.remote.CoinNetworkDataSourceImpl
import com.market.crypto.domain.details.GetCoinChartUseCase
import com.market.crypto.domain.details.GetCoinDetailsUseCase
import com.market.crypto.domain.market.GetCoinUseCase
import com.market.crypto.domain.market.GetMarketStatsUseCase
import com.market.crypto.domain.market.UpdateCachedCoinsUseCase
import com.market.crypto.domain.search.GetCoinSearchResultsUseCase
import com.market.crypto.ui.screens.market.MarketViewModel
import com.market.crypto.ui.screens.details.DetailsViewModel
import com.market.crypto.ui.screens.search.SearchViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HeadersBuilder
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
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
    viewModelOf(::DetailsViewModel)
    viewModelOf(::SearchViewModel)
}

val repositoryModule = module {
    singleOf(::CoinRepositoryImpl).bind<CoinRepository>()
    singleOf(::MarketStatsRepositoryImpl).bind<MarketStatsRepository>()
    singleOf(::CoinDetailsRepositoryImpl).bind<CoinDetailsRepository>()
    singleOf(::CoinChartRepositoryImpl).bind<CoinChartRepository>()
    singleOf(::CoinSearchResultsRepositoryImpl).bind<CoinSearchResultsRepository>()
}

val mapperModule = module {
    single { CoinMapper() }
    single { MarketStatsMapper() }
    single { CoinChartMapper() }
    single { CoinDetailsMapper() }
    single { CoinSearchResultsMapper() }
}

val dataSourceModule = module {
    single { CoinNetworkDataSourceImpl(get()) }.bind<CoinNetworkDataSource>()
    single { CoinLocalDataSourceImpl(get()) }.bind<CoinLocalDataSource>()
}

val useCasesModules = module {
    single { GetCoinUseCase(get()) }
    single { UpdateCachedCoinsUseCase(get()) }
    single { GetMarketStatsUseCase(get()) }
    single { GetCoinDetailsUseCase(get()) }
    single { GetCoinChartUseCase(get()) }
    single { GetCoinSearchResultsUseCase(get()) }
}

val networkModule = module {
    singleOf(::createHttpClient)
}

fun createHttpClient(): HttpClient {
    return HttpClient {
        defaultRequest {
            headers.appendIfNameAbsent("x-access-token",Constants.API_KEY)
        }
        install(Logging) {
            logger = object : Logger{
                override fun log(message: String) {
                    println(message)
                }

            }
            level = LogLevel.ALL
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