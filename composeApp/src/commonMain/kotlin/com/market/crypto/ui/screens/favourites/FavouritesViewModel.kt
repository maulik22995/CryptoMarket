package com.market.crypto.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.domain.market.GetFavouriteCoinsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class FavouritesViewModel(
    private val getFavouriteCoinsUseCase: GetFavouriteCoinsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouritesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        fetchFavouriteCoins()
    }

    private fun fetchFavouriteCoins() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        getFavouriteCoinsUseCase().onEach { favouriteCoins ->
            _uiState.update {
                it.copy(
                    favouriteCoins = favouriteCoins.toImmutableList(),
                    isLoading = false,
                    isEmpty = favouriteCoins.isEmpty(),
                    errorMessage = null
                )
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        fetchFavouriteCoins()
    }
}

