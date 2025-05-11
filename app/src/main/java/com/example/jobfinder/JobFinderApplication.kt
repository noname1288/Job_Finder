package com.example.jobfinder

import android.app.Application
import com.example.jobfinder.data.local.UserSessionManager

class JobFinderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //initialize UserSessionManager
        UserSessionManager.initialize(this)

    }
}