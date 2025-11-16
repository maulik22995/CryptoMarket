package com.market.crypto.domain.market

import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.database.model.FavouriteCoin

class ToggleFavouriteCoinUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(favouriteCoin: FavouriteCoin) {
        coinRepository.toggleIsCoinFavourite(favouriteCoin)
    }
} 