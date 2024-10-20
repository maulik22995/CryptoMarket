package com.market.crypto.ui.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.data.repository.coin.CoinRepository
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.ui.model.MarketUiState
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.error_coins_unavailable
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketViewModel(private val coinRepository: CoinRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCoins()
    }

    private fun fetchCoins(coinSort: CoinSort = CoinSort.Popular,showLoading : Boolean = true) {
        _uiState.update { it.copy(isLoading = showLoading) }
        viewModelScope.launch {
            try {
                val response = coinRepository.getRemoteCoins(
                    coinSort = coinSort,
                    currency = Currency.USD
                )


                when (response) {
                    is com.market.crypto.common.Result.Success -> {
                        response.data.data?.let { data ->
                            _uiState.update {
                                it.copy(
                                    coins = data.coins.toImmutableList(),
                                    isLoading = false
                                )
                            }
                        }
                    }

                    is com.market.crypto.common.Result.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessageIds = it.errorMessageIds + Res.string.error_coins_unavailable,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessageIds = it.errorMessageIds + Res.string.error_coins_unavailable,
                        isLoading = false
                    )
                }
            }
        }

    }

    fun updateCoinSort(coinSort: CoinSort){
        _uiState.update {
            it.copy(coinSort = coinSort)
        }
        fetchCoins(coinSort,false)
    }
}