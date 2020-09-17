package com.maxdexter.myrecipe

import androidx.lifecycle.LifecycleOwner
import androidx.multidex.MultiDexApplication
import com.maxdexter.myrecipe.di.appModule

import org.koin.android.ext.android.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}