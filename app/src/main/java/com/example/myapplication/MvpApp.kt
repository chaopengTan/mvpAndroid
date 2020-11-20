package com.example.myapplication

import android.app.Application
import net.util.manager.config.AppOpts
import net.util.manager.config.ClientOpts

class MvpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ClientOpts.isDebug = BuildConfig.DEBUG
        AppOpts.initContent(this)
    }

    init {
        var map = mapOf("X-Access-Token" to "",
            "platform-type" to "android",
            "app-version" to "1.0.0",
            "device_token" to "sjso930ksd01k24"
            )
        ClientOpts.URL_GITHUB="https://xiaoyuan-api.gymooit.cn"
        ClientOpts.initParameter(map)
    }
}