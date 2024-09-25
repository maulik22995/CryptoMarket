package com.market.crypto.data.source.local.model

import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.currency_eur
import cryptomarket.composeapp.generated.resources.currency_gbp
import cryptomarket.composeapp.generated.resources.currency_usd
import cryptomarket.composeapp.generated.resources.market_coin_sort_gainers
import cryptomarket.composeapp.generated.resources.market_coin_sort_losers
import cryptomarket.composeapp.generated.resources.market_coin_sort_market_cap
import cryptomarket.composeapp.generated.resources.market_coin_sort_newest
import cryptomarket.composeapp.generated.resources.market_coin_sort_popular
import org.jetbrains.compose.resources.StringResource

enum class CoinSort(val nameId: StringResource) {
    MarketCap(Res.string.market_coin_sort_market_cap),
    Popular(Res.string.market_coin_sort_popular),
    Gainers(Res.string.market_coin_sort_gainers),
    Losers(Res.string.market_coin_sort_losers),
    Newest(Res.string.market_coin_sort_newest),
}

enum class Currency(val symbol: String, val nameId: StringResource) {
    USD("$", Res.string.currency_usd),
    GBP("£", Res.string.currency_gbp),
    EUR("€", Res.string.currency_eur)
}


fun Currency.toCurrencyUUID(): String {
    return when (this) {
        Currency.USD -> "yhjMzLPhuIDl"
        Currency.GBP -> "Hokyui45Z38f"
        Currency.EUR -> "5k-_VTxqtCEI"
    }
}

fun CoinSort.getOrderBy(): String {
    return when (this) {
        CoinSort.MarketCap -> "marketCap"
        CoinSort.Popular -> "24hVolume"
        CoinSort.Gainers -> "change"
        CoinSort.Losers -> "change"
        CoinSort.Newest -> "listedAt"
    }
}

fun CoinSort.getOrderDirection(): String {
    return when (this) {
        CoinSort.MarketCap -> "desc"
        CoinSort.Popular -> "desc"
        CoinSort.Gainers -> "desc"
        CoinSort.Losers -> "asc"
        CoinSort.Newest -> "desc"
    }
}