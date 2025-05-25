package com.market.crypto.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.crypto.domain.search.GetCoinSearchResultsUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class SearchViewModel (
    private val getCoinSearchResultsUseCase : GetCoinSearchResultsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        initialUiState()
    }

    @OptIn(FlowPreview::class)
    private fun initialUiState() {
        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if(query.isNotBlank()){
                    _uiState.update {
                        it.copy(isSearching = true)
                    }
                    println("getCoinSearchResultsUseCase >>")
                   getCoinSearchResultsUseCase.invoke(query).collectLatest { searchResult ->

                       when(searchResult){
                           is com.market.crypto.common.Result.Error -> {
                               _uiState.update {
                                   it.copy(
                                       errorMessage = searchResult.message,
                                       isSearching = false
                                   )
                               }
                           }

                           is com.market.crypto.common.Result.Success -> {
                               val searchResults = searchResult.data.toPersistentList()

                               _uiState.update {
                                   it.copy(
                                       searchResults = searchResults,
                                       queryHasNoResults = searchResults.isEmpty(),
                                       isSearching = false,
                                       errorMessage = null
                                   )
                               }
                           }
                       }
                   }

                }else{
                    _uiState.update {
                        it.copy(
                            searchResults = persistentListOf(),
                            queryHasNoResults = false,
                            isSearching = false,
                            errorMessage = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }
}