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
import cryptomarket.composeapp.generated.resources.empty_state_search_query
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchQueryEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        image = painterResource(Res.drawable.empty_state_search_query),
        title = "Explore coins",
        subtitle = {
            Text(
                text = "Search by name or symbol",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.onSurfaceVariant
            )
        },
        modifier = modifier.padding(bottom = 12.dp)
    )
}