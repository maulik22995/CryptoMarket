package com.market.crypto.ui.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.market.crypto.extension.formattedAmount
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.empty_chart_range_message
import cryptomarket.composeapp.generated.resources.range_title_high
import cryptomarket.composeapp.generated.resources.range_title_low
import org.jetbrains.compose.resources.stringResource

@Composable
fun CoinChartRangeCard(
    currentPrice: String,
    minPrice: String,
    maxPrice: String,
    isPricesEmpty: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        if (!isPricesEmpty) {
            Column(modifier = Modifier.padding(12.dp)) {
                ChartRangeLine(
                    currentPrice,
                    minPrice,
                    maxPrice,
                    Modifier.fillMaxWidth().height(20.dp).padding(horizontal = 4.dp)
                )

                Spacer(Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = stringResource(Res.string.range_title_low),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = minPrice.formattedAmount(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(Res.string.range_title_high),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = maxPrice.formattedAmount(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().height(86.dp)) {
                Text(
                    text = stringResource(Res.string.empty_chart_range_message),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}