package com.market.crypto.domain.market

import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow

class GetFavouriteCoinsUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(): Flow<List<FavouriteCoin>> {
        return coinRepository.getFavouriteCoins()
    }
} 