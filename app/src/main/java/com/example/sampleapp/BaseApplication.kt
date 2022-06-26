package com.example.sampleapp

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import android.content.Context

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        private var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}