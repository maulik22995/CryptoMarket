package com.market.crypto.ui.screens.search

import com.market.crypto.model.SearchCoin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchUiState(
    val searchResults: ImmutableList<SearchCoin> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val errorMessage: String? = null,
    val isSearching: Boolean = false
)
