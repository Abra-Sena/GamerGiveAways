package com.emissa.apps.gamergiveaways

import android.app.Application
import com.emissa.apps.gamergiveaways.di.applicationModule
import com.emissa.apps.gamergiveaways.di.networkModule
import com.emissa.apps.gamergiveaways.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiveawaysApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GiveawaysApp)
            modules(listOf(networkModule, applicationModule, viewModelModule))
        }
    }
}