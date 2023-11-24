package com.thex.bookshare.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BookShareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isDev = true
        if (isDev) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberInitializerTree())
        }

    }
}