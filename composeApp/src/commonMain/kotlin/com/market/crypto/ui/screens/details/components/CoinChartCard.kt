package com.market.crypto.ui.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.market.crypto.ui.components.PriceGraph
import com.market.crypto.ui.model.ChartPeriod
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.empty_chart_message
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round


@Composable
fun CoinChartCard(
    currentPrice: String,
    prices: ImmutableList<Double>,
    periodPriceChangePercentage: String,
    chartPeriod: ChartPeriod,
    onClickChartPeriod: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(shape = MaterialTheme.shapes.medium, modifier = modifier) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {

            Column(modifier = Modifier.padding(horizontal = 12.dp)) {

                val price = currentPrice.toDoubleOrNull()?.let {
                    round(it * 100) / 100  // Round to 2 decimal places
                }?.toString() ?: "0.00"  // Default value if conversion fails

                Text(
                    text = "$$price",
                    color = LocalAppColors.current.themeLightDark,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                )

                val isDown = periodPriceChangePercentage.startsWith("-")
                val change =
                    if (isDown) "${periodPriceChangePercentage}%" else "+${periodPriceChangePercentage}%"


                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = change,
                        color = LocalAppColors.current.textThemeColor,
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                if (isDown) LocalAppColors.current.marketDown else LocalAppColors.current.marketUp,
                                shape = RoundedCornerShape(5.dp),
                            ).padding(vertical = 1.dp, horizontal = 6.dp)
                    )

                    Spacer(Modifier.padding(4.dp))

                    if (currentPrice.isNotEmpty()) {
                        Text(
                            text = chartPeriod.longNameId,
                            color = LocalAppColors.current.themeLightDark
                        )
                    }

                }

                Spacer(Modifier.height(4.dp))

                if (prices.isNotEmpty()) {
                    PriceGraph(
                        prices,
                        periodPriceChangePercentage,
                        true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                } else {
                    Box(

                    ) {
                        Text(
                            text = stringResource(Res.string.empty_chart_message),
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                ChartPeriodSelector(
                    selectedChartPeriod = chartPeriod,
                    onChartPeriod = onClickChartPeriod,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }


        }

    }
}
