package com.example.myapplication.api

import com.base.mvp.transit.IApiServerAbs
import com.google.auto.service.AutoService

@AutoService(HTTPApi::class)
class IApi : IApiServerAbs<HTTPApi>() {
    override fun getServer(): Class<HTTPApi> {
        return HTTPApi::class.java
    }
}