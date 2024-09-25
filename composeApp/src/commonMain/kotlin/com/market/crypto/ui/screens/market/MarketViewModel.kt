package com.market.crypto.ui.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import kotlinx.coroutines.launch

class MarketViewModel(private val coinRepository: CoinRepository) : ViewModel() {
    init {
        viewModelScope.launch {
           val response = coinRepository.getRemoteCoins(coinSort = CoinSort.Popular, currency = Currency.USD)
        }
    }
}