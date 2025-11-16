package com.market.crypto.ui.screens.favourites

import com.market.crypto.data.source.local.database.model.FavouriteCoin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavouritesUiState(
    val favouriteCoins: ImmutableList<FavouriteCoin> = persistentListOf(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessage: String? = null
)

