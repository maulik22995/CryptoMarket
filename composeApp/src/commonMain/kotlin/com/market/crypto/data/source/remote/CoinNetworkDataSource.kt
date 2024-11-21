package com.market.crypto.data.source.remote

import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.model.CoinsApiModel

interface CoinNetworkDataSource {
    suspend fun getCoins(coinSort: CoinSort, currency: Currency): CoinsApiModel
}