package com.market.crypto.ui.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.domain.market.GetCoinUseCase
import com.market.crypto.domain.market.GetMarketStatsUseCase
import com.market.crypto.domain.market.UpdateCachedCoinsUseCase
import com.market.crypto.ui.model.MarketUiState
import com.market.crypto.ui.model.TimeOfDay
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.error_coins_unavailable
import cryptomarket.composeapp.generated.resources.error_market_stats
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.minutes

class MarketViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    private val updateCachedCoinsUseCase: UpdateCachedCoinsUseCase,
    private val getMarketStatsUseCase: GetMarketStatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCoins()
    }

    private fun fetchCoins(coinSort: CoinSort = CoinSort.Popular, showLoading: Boolean = true) {
        _uiState.update { it.copy(isLoading = showLoading) }
        getCoinUseCase().onEach { coinsResult ->
            if (coinsResult.isEmpty()) {
                _uiState.update {
                    it.copy(
                        errorMessageIds = it.errorMessageIds + Res.string.error_coins_unavailable,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        coins = coinsResult.toImmutableList(),
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)

        viewModelScope.launch {
            updateCachedCoins(coinSort, Currency.USD)
        }

        getCurrentHourFlow().onEach { currentHour ->
            val timeOfDay = calculateTimeOfDay(currentHour)
            _uiState.update {
                it.copy(timeOfDay = timeOfDay)
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            when (val marketStatsResult = getMarketStatsUseCase()) {
                is Result.Success -> {
                    val marketStats = marketStatsResult.data

                    _uiState.update {
                        it.copy(
                            marketCapChangePercentage24h = marketStats.marketCapChangePercentage24h
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        val errorMessages = it.errorMessageIds + Res.string.error_market_stats

                        it.copy(
                            errorMessageIds = errorMessages
                        )
                    }
                }
            }
        }

    }


    private fun calculateTimeOfDay(hour: Int): TimeOfDay {
        return when (hour) {
            in 4..11 -> TimeOfDay.Morning
            in 12..17 -> TimeOfDay.Afternoon
            else -> TimeOfDay.Evening
        }
    }


    private fun getCurrentHourFlow(): Flow<Int> = flow {
        while (true) {
            val currentHour =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
            emit(currentHour)
            delay(5.minutes.inWholeMicroseconds)
        }
    }

    private suspend fun updateCachedCoins(coinSort: CoinSort, currency: Currency) {
        val result = updateCachedCoinsUseCase(coinSort = coinSort, currency = currency)

        if (result is Result.Error) {
            _uiState.update {
                val errorMessages = it.errorMessageIds + Res.string.error_coins_unavailable
                it.copy(errorMessageIds = errorMessages)
            }
        }
    }

    fun updateCoinSort(coinSort: CoinSort) {
        _uiState.update {
            it.copy(coinSort = coinSort)
        }
        viewModelScope.launch {
            updateCachedCoins(coinSort, Currency.USD)
        }
    }
}