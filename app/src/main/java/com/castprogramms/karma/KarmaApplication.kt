package com.castprogramms.karma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KarmaApplication : Application() {
    val module = {

    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KarmaApplication)
            module
        }
    }
}