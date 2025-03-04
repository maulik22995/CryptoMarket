package com.market.crypto.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.market.crypto.ui.theme.CryptoMarketTheme
import com.market.crypto.ui.theme.LocalAppColors

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = LocalAppColors.current.primary,
            strokeWidth = 5.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun LoadingIndicatorPreview() {
    CryptoMarketTheme {
        LoadingIndicator()
    }
}
