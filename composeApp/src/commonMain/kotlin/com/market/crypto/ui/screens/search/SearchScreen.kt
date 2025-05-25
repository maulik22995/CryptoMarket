package com.market.crypto.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.market.crypto.model.SearchCoin
import com.market.crypto.ui.components.ErrorState
import com.market.crypto.ui.components.LoadingIndicator
import com.market.crypto.ui.screens.search.components.SearchListItem
import com.market.crypto.ui.screens.search.components.SearchQueryEmptyState
import com.market.crypto.ui.screens.search.components.SearchResultsEmptyState
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.ic_close
import cryptomarket.composeapp.generated.resources.ic_market
import cryptomarket.composeapp.generated.resources.ic_search
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onNavigateDetails : (id:String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SearchScreen(
        uiState = uiState,
        searchQuery = viewModel.searchQuery,
        onSearchQueryChange = { searchQuery ->
            viewModel.updateSearchQuery(searchQuery)
        },
        onCoinClick = { coin ->
            onNavigateDetails(coin.id)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCoinClick: (SearchCoin) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        colors = SearchBarDefaults.colors(
            containerColor = LocalAppColors.current.background,
            dividerColor = LocalAppColors.current.surface
        ),
        tonalElevation = 0.dp,
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = { keyboardController?.hide() },
                expanded = true,
                onExpandedChange = { },
                placeholder = { Text("Search coins") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if(searchQuery.isNotEmpty()){
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = "Cancel",
                            modifier = Modifier.size(20.dp).clickable {
                                onSearchQueryChange("")
                            }
                        )
                    }
                },
            )
        },
        expanded = true,
        onExpandedChange = { },
    ) {
        when{
            uiState.isSearching -> {
                LoadingIndicator()
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    modifier = Modifier.padding(12.dp)
                )
            }

            else -> {
                SearchContent(
                    searchQuery = searchQuery,
                    searchResults = uiState.searchResults,
                    queryHasNoResults = uiState.queryHasNoResults,
                    onCoinClick = onCoinClick
                )
            }
        }
    }
}

@Composable
fun SearchContent(
    searchQuery: String,
    searchResults: ImmutableList<SearchCoin>,
    queryHasNoResults: Boolean,
    onCoinClick: (SearchCoin) -> Unit,
    modifier: Modifier = Modifier
){
    when{
        searchQuery.isEmpty() -> {
            SearchQueryEmptyState()
        }
        queryHasNoResults -> {
            SearchResultsEmptyState()
        }
        else -> {
            LazyColumn (
                contentPadding = PaddingValues(12.dp),
                modifier = modifier
            ){
                items(
                    count = searchResults.size,
                    key = {index -> searchResults[index].id},
                    itemContent = {
                        index: Int ->
                        val searchCoin = searchResults[index]

                        val cardShape = when {
                            searchResults.size == 1 -> MaterialTheme.shapes.medium

                            index == 0 -> MaterialTheme.shapes.medium.copy(
                                bottomStart = CornerSize(0.dp),
                                bottomEnd = CornerSize(0.dp)
                            )

                            index == searchResults.lastIndex ->
                                MaterialTheme.shapes.medium.copy(
                                    topStart = CornerSize(0.dp),
                                    topEnd = CornerSize(0.dp)
                                )

                            else -> RoundedCornerShape(0.dp)
                        }

                        SearchListItem(
                            searchCoin = searchCoin,
                            onCoinClick = onCoinClick,
                            cardShape = cardShape
                        )
                    }
                )
            }
        }
    }

}