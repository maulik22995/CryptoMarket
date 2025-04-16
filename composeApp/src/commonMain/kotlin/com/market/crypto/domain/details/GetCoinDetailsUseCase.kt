package com.market.crypto.domain.details

import com.market.crypto.common.Result
import com.market.crypto.data.repository.details.CoinDetailsRepository
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.model.CoinDetails
import kotlinx.coroutines.flow.Flow

class GetCoinDetailsUseCase(
    private val coinDetailsRepository: CoinDetailsRepository,
) {
    operator fun invoke(coinId: String, currency: Currency): Flow<Result<CoinDetails>> {
        val response =  coinDetailsRepository.getCoinDetails(
            coinId = coinId,
            currency = currency
        )
        println("GetCoinDetailsUseCase >>" + response)
        return response
    }
}
