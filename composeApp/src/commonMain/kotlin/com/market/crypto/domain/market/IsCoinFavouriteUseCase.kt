package com.market.crypto.domain.market

import com.market.crypto.data.repository.coin.CoinRepository
import cryptomarket.composeapp.generated.resources.Res
import kotlinx.coroutines.flow.Flow

class IsCoinFavouriteUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(favouriteCoinId: String): Flow<Boolean> {
        return coinRepository.isCoinFavourite(favouriteCoinId)
    }
} 