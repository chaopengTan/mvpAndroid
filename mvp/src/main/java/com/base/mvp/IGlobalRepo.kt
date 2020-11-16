package com.base.mvp

import com.base.mvp.model.RequestModelBean
import okhttp3.RequestBody


interface IGlobalRepo<T> {
    fun getHttpService(): T
    fun createRequestBody(request: RequestModelBean): RequestBody
    fun createRequestMap(request: RequestModelBean): Map<String, String>
}