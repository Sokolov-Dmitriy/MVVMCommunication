package com.sokolovds.mvvmcommunication

import android.app.Application
import com.sokolovds.mvvmcommunication.di.dataModule
import com.sokolovds.mvvmcommunication.di.domainModule
import com.sokolovds.mvvmcommunication.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}