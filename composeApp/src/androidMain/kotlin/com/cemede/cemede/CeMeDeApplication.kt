package com.cemede.cemede

import android.app.Application
import com.cemede.cemede.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CeMeDeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CeMeDeApplication)
        }
    }
}
