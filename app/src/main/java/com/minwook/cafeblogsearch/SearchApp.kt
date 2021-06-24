package com.minwook.cafeblogsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
