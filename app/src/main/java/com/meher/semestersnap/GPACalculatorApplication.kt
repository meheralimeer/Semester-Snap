package com.meher.semestersnap

import android.app.Application
import com.meher.semestersnap.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GPACalculatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GPACalculatorApplication)
            modules(appModule)
        }
    }
}