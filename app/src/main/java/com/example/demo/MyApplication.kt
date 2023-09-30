package com.example.demo

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()

        instance = this

        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

    }

    companion object {
        private lateinit var instance: MyApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}