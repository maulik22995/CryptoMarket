package com.market.crypto.ui.screens.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.market.crypto.ui.components.EmptyState
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.empty_state_search_results
import org.jetbrains.compose.resources.painterResource


@Composable
fun SearchResultsEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        image = painterResource(Res.drawable.empty_state_search_results),
        title = "No coin found",
        subtitle = {
            Text(
                text = "Try adjusting your search",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.onSurfaceVariant
            )
        },
        modifier = modifier.padding(bottom = 12.dp)
    )
}

