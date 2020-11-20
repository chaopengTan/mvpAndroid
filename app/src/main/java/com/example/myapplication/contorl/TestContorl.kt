package com.example.myapplication.contorl

import com.base.mvp.base.IBaseModel
import com.base.mvp.base.IBasePresenter
import com.base.mvp.base.IBaseView
import com.example.myapplication.bo.LoginBO
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface TestContorl {
    interface View : IBaseView{
        fun showResults(results:String)
    }
    interface Presenter : IBasePresenter<View>{
        fun login(userName:String,vCode:String)
    }
    interface Model : IBaseModel{
        fun login(loginBO: LoginBO): Deferred<Response<String>>
    }
}