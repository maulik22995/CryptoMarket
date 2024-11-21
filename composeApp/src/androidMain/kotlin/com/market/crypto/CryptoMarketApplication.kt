package com.market.crypto

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.market.crypto.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class CryptoMarketApplication : Application(), KoinComponent {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initKoin {
            androidLogger()
            androidContext(this@CryptoMarketApplication)
        }
    }
}