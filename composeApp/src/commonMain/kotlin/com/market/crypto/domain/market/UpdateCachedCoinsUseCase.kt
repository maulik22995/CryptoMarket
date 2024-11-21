package com.market.crypto.domain.market

import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency

class UpdateCachedCoinsUseCase(
    private val coinRepository: CoinRepository
) {
    suspend operator fun invoke(
        coinSort: CoinSort,
        currency: Currency
    ): com.market.crypto.common.Result<List<Coin>> {
        return refreshCachedCoins(coinSort = coinSort, currency = currency)
    }

    private suspend fun refreshCachedCoins(
        coinSort: CoinSort,
        currency: Currency
    ): com.market.crypto.common.Result<List<Coin>> {
        val remoteCoinsResult = coinRepository.getRemoteCoins(
            coinSort = coinSort,
            currency = currency
        )

        if (remoteCoinsResult is com.market.crypto.common.Result.Success) {
            val coins = remoteCoinsResult.data
            coinRepository.updateCachedCoins(coins = coins)
        }

        return remoteCoinsResult
    }
}
