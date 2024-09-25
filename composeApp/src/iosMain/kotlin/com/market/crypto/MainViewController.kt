package com.market.crypto

import androidx.compose.ui.window.ComposeUIViewController
import com.market.crypto.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) { App() }