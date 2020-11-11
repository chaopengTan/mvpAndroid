package com.example.myapplication

import android.app.Application
import net.util.manager.config.AppOpts
import net.util.manager.config.ClientOpts

class MvpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppOpts.initContent(this)

    }

    init {
        ClientOpts.isDebug = BuildConfig.DEBUG
    }
}