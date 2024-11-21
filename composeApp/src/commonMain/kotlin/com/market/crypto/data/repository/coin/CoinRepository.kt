package com.market.crypto.data.repository.coin

import com.market.crypto.common.Result
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.data.source.remote.model.CoinsApiModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getRemoteCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<Coin>>

    fun getCachedCoins(): Flow<List<Coin>>
    suspend fun updateCachedCoins(coins: List<Coin>)
}