package com.castprogramms.karma

import android.app.Application
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.ServiceFireStore
import com.castprogramms.karma.ui.addServices.AddServiceViewModel
import com.castprogramms.karma.ui.allServices.ServicesViewModel
import com.castprogramms.karma.ui.gallery.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KarmaApplication : Application() {
    val appModule = module{
        single { ServiceFireStore() }
        single { Repository(get()) }
        viewModel { ServicesViewModel(get()) }
        viewModel { AddServiceViewModel(get()) }
        viewModel { ProfileViewModel(get()) }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KarmaApplication)
            modules(appModule)
        }
    }
}