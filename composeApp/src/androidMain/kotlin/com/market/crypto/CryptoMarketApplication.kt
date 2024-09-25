package com.market.crypto

import android.app.Application
import com.market.crypto.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class CryptoMarketApplication : Application(),KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CryptoMarketApplication)
        }
    }
}