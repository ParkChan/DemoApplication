package com.example.demo

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

    }
}