package com.market.crypto.ui.screens.market

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MarketScreen(marketViewModel: MarketViewModel = koinViewModel<MarketViewModel>()) {
    Text("Market")
}