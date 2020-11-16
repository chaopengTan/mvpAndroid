package com.base.mvp.model

import com.base.mvp.IGlobalRepo
import okhttp3.RequestBody
import com.google.gson.Gson
import okhttp3.MediaType
import android.text.TextUtils


class MvpModel<T> : IGlobalRepo<T>{
    override fun getHttpService(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRequestMap(obj: RequestModelBean): Map<String, String> {
        val map = HashMap<String,String>()
        val clazz = obj.javaClass
        for (field in clazz.declaredFields) {
            field.isAccessible = true
            val fieldName = field.name
            var value = ""
            try {
                value = if (field.get(obj) != null) {
                    field.get(obj).toString()
                } else {
                    ""
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            if (!TextUtils.isEmpty(value)) {
                map[fieldName] = value
            }
        }
        return map
    }

    override fun createRequestBody(request: RequestModelBean): RequestBody {
        return RequestBody.create(MediaType.parse("application/json"), Gson().toJson(request))
    }

//    override fun getHttpService(): T {
////        return HttpManager.getService(
////            IApiServer::class.java!!,
////            ClientOpts.URL_GITHUB,
////            Client::class.java
////        )
//    }


}