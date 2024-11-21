package com.market.crypto.ui.model

import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.remote.model.CoinApiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.StringResource

data class MarketUiState(
    val coins: ImmutableList<Coin> = persistentListOf(),
    val timeOfDay: TimeOfDay = TimeOfDay.Morning,
//    val marketCapChangePercentage24h: Percentage? = null,
    val coinSort: CoinSort = CoinSort.Popular,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageIds: List<StringResource> = persistentListOf()
)
