package com.market.crypto.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.market.crypto.ui.theme.CryptoMarketTheme
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.error_occurred
import cryptomarket.composeapp.generated.resources.error_state
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorState(
    message: String?,
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(Res.drawable.error_state),
        title = stringResource(Res.string.error_occurred),
        subtitle = {
            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalAppColors.current.onSurfaceVariant
                )
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview()
private fun ErrorStatePreview() {
    CryptoMarketTheme {
        ErrorState(
            message = "No internet connection",
        )
    }
}
