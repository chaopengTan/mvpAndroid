package com.example.myapplication.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.POST

interface HTTPApi {
    @POST("/v1/customer/smsLogin")
    fun login(requestBody: RequestBody): Deferred<Response<String>>
}