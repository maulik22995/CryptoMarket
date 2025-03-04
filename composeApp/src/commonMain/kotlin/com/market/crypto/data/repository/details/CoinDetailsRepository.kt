package com.market.crypto.data.repository.details

import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.model.CoinDetails

import kotlinx.coroutines.flow.Flow

interface CoinDetailsRepository {
    fun getCoinDetails(coinId: String, currency: Currency): Flow<Result<CoinDetails>>
}
