package com.market.crypto.domain.market

import com.market.crypto.common.Result
import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.database.model.Coin
import kotlinx.coroutines.flow.Flow

class GetCoinUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(): Flow<List<Coin>> {
        return getCoins()
    }

    private fun getCoins(): Flow<List<Coin>> {
        return coinRepository.getCachedCoins()
    }
}