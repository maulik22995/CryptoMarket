package com.market.crypto.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ChipColors
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.ui.theme.CryptoMarketTheme
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.ic_gainer
import cryptomarket.composeapp.generated.resources.ic_looser
import cryptomarket.composeapp.generated.resources.ic_market_cap
import cryptomarket.composeapp.generated.resources.ic_newest
import cryptomarket.composeapp.generated.resources.ic_popular
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CoinSortChip(
    coinSort: CoinSort,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        onClick = onClick,
        selected = selected,
        leadingIcon = {
            val imageVector = when (coinSort) {
                CoinSort.MarketCap -> Res.drawable.ic_market_cap
                CoinSort.Popular -> Res.drawable.ic_popular
                CoinSort.Gainers -> Res.drawable.ic_gainer
                CoinSort.Losers -> Res.drawable.ic_looser
                CoinSort.Newest -> Res.drawable.ic_newest
            }

            Icon(
                painter = painterResource(imageVector),
                tint = if (selected) {
                    LocalAppColors.current.onSurface
                } else {
                    LocalAppColors.current.onSurfaceVariant
                },
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = LocalAppColors.current.background,
            labelColor = LocalAppColors.current.onSurfaceVariant,
            selectedContainerColor = LocalAppColors.current.surface,
            selectedLabelColor = LocalAppColors.current.onSurface,
        ),
        shape = MaterialTheme.shapes.small,
        label = { Text(text = stringResource(coinSort.nameId)) },
        border = null,
        modifier = modifier
    )
}

@Preview
@Composable
private fun CoinSortChipUnselectedPreview() {
    CryptoMarketTheme {
        CoinSortChip(
            coinSort = CoinSort.MarketCap,
            selected = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CoinSortChipSelectedPreview() {
    CryptoMarketTheme {
        CoinSortChip(
            coinSort = CoinSort.Gainers,
            selected = true,
            onClick = {}
        )
    }
}