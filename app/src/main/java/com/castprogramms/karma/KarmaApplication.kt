package com.castprogramms.karma

import android.app.Application
import com.castprogramms.karma.network.*
import com.castprogramms.karma.ui.addServices.AddServiceViewModel
import com.castprogramms.karma.ui.allServices.ServicesViewModel
import com.castprogramms.karma.ui.info.InfoViewModel
import com.castprogramms.karma.ui.myService.MyServiceViewModel
import com.castprogramms.karma.ui.insertdata.InsertDataViewModel
import com.castprogramms.karma.ui.login.LoginViewModel
import com.castprogramms.karma.ui.news.NewsViewModel
import com.castprogramms.karma.ui.profileuser.ProfileUserViewModel
import com.castprogramms.karma.ui.service.ServiceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class KarmaApplication : Application() {
    val appModule = module{
        single { ServiceFireStore() }
        single { ManageUserDataFireStore() }
        single { NewsFireStore() }
        single { SettingsFireStore() }
        single { Repository(get(), get(), get(), get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { ServicesViewModel(get()) }
        viewModel { AddServiceViewModel(get()) }
        viewModel { MyServiceViewModel(get()) }
        viewModel { InsertDataViewModel(get()) }
        viewModel { ServiceViewModel(get()) }
        viewModel { ProfileUserViewModel(get()) }
        viewModel { NewsViewModel(get()) }
        viewModel { InfoViewModel(get()) }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@KarmaApplication)
            modules(appModule)
        }
    }
}
