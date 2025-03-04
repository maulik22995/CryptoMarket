package com.market.crypto.ui.screens.details

import com.market.crypto.model.CoinChart
import com.market.crypto.model.CoinDetails
import com.market.crypto.ui.model.ChartPeriod

sealed interface DetailsUiState {
    data class Success(
        val coinDetails: CoinDetails,
        val coinChart: CoinChart,
        val chartPeriod: ChartPeriod,
        val isCoinFavourite: Boolean
    ) : DetailsUiState

    data class Error(val message: String?) : DetailsUiState
    data object Loading : DetailsUiState
}
