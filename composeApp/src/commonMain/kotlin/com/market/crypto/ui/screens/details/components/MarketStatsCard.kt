package com.market.crypto.ui.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.market.crypto.extension.formatDate
import com.market.crypto.extension.formattedAmount
import com.market.crypto.model.CoinDetails
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.list_item_ath
import cryptomarket.composeapp.generated.resources.list_item_ath_date
import cryptomarket.composeapp.generated.resources.list_item_circulating_supply
import cryptomarket.composeapp.generated.resources.list_item_listed_date
import cryptomarket.composeapp.generated.resources.list_item_market_cap
import cryptomarket.composeapp.generated.resources.list_item_market_cap_rank
import cryptomarket.composeapp.generated.resources.list_item_volume_24h
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MarketStatsCard(
    coinDetails: CoinDetails,
    modifier: Modifier = Modifier
){
    val coinDetailItems = remember(coinDetails) {
        listOf(
            CoinDetailsListItem(
                nameId = Res.string.list_item_market_cap_rank,
                value = coinDetails.marketCapRank
            ),
            CoinDetailsListItem(
                nameId = Res.string.list_item_market_cap,
                value = coinDetails.marketCap.formattedAmount()
            ),
            CoinDetailsListItem(
                nameId =  Res.string.list_item_ath,
                value = coinDetails.allTimeHigh.formattedAmount()
            ),
            CoinDetailsListItem(
                nameId = Res.string.list_item_ath_date,
                value = formatDate(coinDetails.allTimeHighDate)
            ),
            CoinDetailsListItem(
                nameId = Res.string.list_item_volume_24h,
                value = coinDetails.volume24h.formattedAmount()
            ),
            CoinDetailsListItem(
                nameId = Res.string.list_item_circulating_supply,
                value = coinDetails.circulatingSupply
            ),
            CoinDetailsListItem(
                nameId = Res.string.list_item_listed_date,
                value = formatDate(coinDetails.listedDate)
            )

        )
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = LocalAppColors.current.surface
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                coinDetailItems.forEachIndexed { coinDetailsIndex, coinDetailsListItem ->
                    if (coinDetailsIndex != 0) {
                        HorizontalDivider(color = LocalAppColors.current.primaryContainer)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(coinDetailsListItem.nameId),
                            style = MaterialTheme.typography.bodySmall,
                            color = LocalAppColors.current.onSurfaceVariant
                        )

                        Text(
                            text = coinDetailsListItem.value,
                            style = MaterialTheme.typography.bodySmall,
                            color = LocalAppColors.current.onSurface
                        )
                    }
                }
            }
        }
    }
}

private data class CoinDetailsListItem(
    val nameId: StringResource,
    val value: String
)