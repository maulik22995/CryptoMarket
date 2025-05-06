package com.market.crypto.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.market.crypto.ui.theme.CryptoMarketTheme
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.error_coins_unavailable
import cryptomarket.composeapp.generated.resources.ic_looser
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyState(
    image: Painter,
    title: String,
    subtitle: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(LocalAppColors.current.background)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .padding(12.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.height(180.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.onBackground
            )

            Spacer(Modifier.height(4.dp))

            subtitle()
        }
    }
}

@Composable
@Preview()
private fun EmptyStatePreview() {
    CryptoMarketTheme {
        EmptyState(
            image = painterResource(Res.drawable.ic_looser),
            title = "No coins",
            subtitle = {
                Text(
                    text = stringResource(Res.string.error_coins_unavailable),
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalAppColors.current.onSurfaceVariant
                )
            }
        )
    }
}
