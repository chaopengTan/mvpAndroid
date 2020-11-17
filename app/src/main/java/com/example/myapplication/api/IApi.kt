package com.example.myapplication.api
import com.base.mvp.api.IApiServerAbs

class IApi : IApiServerAbs<HTTPApi>() {
    override fun getServer(): Class<HTTPApi> {
        return HTTPApi::class.java
    }
}