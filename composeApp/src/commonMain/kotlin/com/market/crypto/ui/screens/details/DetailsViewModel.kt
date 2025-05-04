package com.market.crypto.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.common.Result
import com.market.crypto.data.source.local.model.Currency
import com.market.crypto.domain.details.GetCoinChartUseCase
import com.market.crypto.domain.details.GetCoinDetailsUseCase
import com.market.crypto.ui.model.ChartPeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

class DetailsViewModel(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val getCoinChartUseCase: GetCoinChartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val chartPeriodFlow = MutableStateFlow(ChartPeriod.Day)

    private val coinId = savedStateHandle.get<String>("coinId")

    init {
        initialiseUiState()
    }

    private fun initialiseUiState() {
        if (coinId == null) {
            _uiState.update { DetailsUiState.Error("Invalid coin ID") }
            return
        }
        val coinDetailsFlow = getCoinDetailsUseCase(coinId = coinId, currency = Currency.USD)
        val coinChartFlow = chartPeriodFlow.flatMapLatest { chartPeriod ->
            getCoinChartUseCase(
                coinId = coinId,
                chartPeriod = chartPeriod.stringName,
                currency = Currency.USD
            )
        }

        combine(
            coinDetailsFlow,
            coinChartFlow
        ){ coinDetailsResult, coinChartResult ->
            when{
                coinDetailsResult is Result.Error -> {
                    _uiState.update { DetailsUiState.Error(coinDetailsResult.message) }
                }

                coinChartResult is Result.Error -> {
                    _uiState.update { DetailsUiState.Error(coinChartResult.message) }
                }

                coinDetailsResult is Result.Success &&
                        coinChartResult is Result.Success  -> {
                    _uiState.update {
                        DetailsUiState.Success(
                            coinDetails = coinDetailsResult.data,
                            coinChart = coinChartResult.data,
                            chartPeriod = chartPeriodFlow.value,
                            isCoinFavourite = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateChartPeriod(chartPeriod: ChartPeriod) {
        chartPeriodFlow.value = chartPeriod
    }
}