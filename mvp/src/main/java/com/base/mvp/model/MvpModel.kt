package com.base.mvp.model

import com.base.mvp.IGlobalRepo
import okhttp3.RequestBody
import com.google.gson.Gson
import okhttp3.MediaType
import android.text.TextUtils
import com.base.mvp.transit.IApiServerAbs
import net.util.manager.Client
import net.util.manager.HttpManager
import java.util.*
import java.util.concurrent.TimeoutException
import kotlin.collections.HashMap


abstract class MvpModel<T> : IGlobalRepo{
    override fun createRequestMap(obj: Any): Map<String, String> {
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

    override fun createRequestBody(request: Any): RequestBody {
        return RequestBody.create(MediaType.parse("application/json"), Gson().toJson(request))
    }

    override fun getHttpService(): T {
        val serviceLoader = ServiceLoader.load(IApiServerAbs::class.java)
        val it = serviceLoader.iterator()
        if(!it.hasNext()){
            throw TimeoutException("it is Null")
        }

        var sl = it.next()
        if(sl==null){
            throw TimeoutException("sl is Null")
        }
        val tClass = sl.getServer()
        return HttpManager.getService(
            tClass,
            Client::class.java
        ) as T
    }
}