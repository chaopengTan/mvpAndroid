package com.example.myapplication.contorl

import com.base.mvp.model.MvpModel
import com.example.myapplication.api.HTTPApi
import com.example.myapplication.bo.LoginBO
import kotlinx.coroutines.Deferred
import retrofit2.Response

class TestModel : MvpModel<HTTPApi>(),TestContorl.Model {
    override fun login(loginBO: LoginBO): Deferred<Response<String>> {
        return getHttpService().login( createRequestBody(loginBO))
    }
}