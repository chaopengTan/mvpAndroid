package com.base.mvp

import okhttp3.RequestBody


interface IGlobalRepo {
    fun getHttpService():Any?
    fun createRequestBody(request: Any): RequestBody
    fun createRequestMap(request: Any): Map<String, String>
}