package com.market.crypto.domain.details

import com.market.crypto.common.Result
import com.market.crypto.data.repository.details.CoinDetailsRepository
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.model.CoinDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class GetCoinDetailsUseCase(
    private val coinDetailsRepository: CoinDetailsRepository,
) {
    operator fun invoke(coinId: String, currency: Currency): Flow<Result<CoinDetails>> {
        return getCoinDetails(coinId = coinId, currency)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCoinDetails(
        coinId: String,
        currency: Currency
    ): Flow<com.market.crypto.common.Result<CoinDetails>> {
        return coinDetailsRepository.getCoinDetails(
            coinId = coinId,
            currency = currency
        )
    }
}
