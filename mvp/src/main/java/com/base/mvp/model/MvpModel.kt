package com.base.mvp.model

import com.base.mvp.IGlobalRepo
import okhttp3.RequestBody
import com.google.gson.Gson
import okhttp3.MediaType
import android.text.TextUtils
import net.util.manage.api.IApiServerAbs
import net.util.manager.Client
import net.util.manager.HttpManager
import net.util.manager.config.ClientOpts
import java.util.*
import kotlin.collections.HashMap


abstract class MvpModel : IGlobalRepo{
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

    override fun getHttpService(): Any? {
        val serviceLoader = ServiceLoader.load(IApiServerAbs::class.java)
        val it = serviceLoader.iterator()
        val tClass =  it.next().getServer()
        return HttpManager.getService(
            tClass,
            ClientOpts.URL_GITHUB,
            Client::class.java
        )
    }
}