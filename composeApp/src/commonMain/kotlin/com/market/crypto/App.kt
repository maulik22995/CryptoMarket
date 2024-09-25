package com.market.crypto

import androidx.compose.runtime.Composable
import com.market.crypto.navigation.AppNavHost
import com.market.crypto.ui.theme.CryptoMarketTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    CryptoMarketTheme(darkTheme = false) {
        KoinContext {
            AppNavHost()
        }
    }
}