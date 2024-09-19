package com.market.crypto.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun CryptoMarketTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) darkExtendedColors else lightExtendedColors

    CompositionLocalProvider(LocalAppColors provides colors) {
        content()
    }
}