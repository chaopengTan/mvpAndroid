package com.example.myapplication.contorl

import android.util.Log
import com.base.mvp.base.BasePresenterImpl
import com.example.myapplication.bo.LoginBO

class TestPresenter : BasePresenterImpl<TestContorl.View, TestContorl.Model>(),TestContorl.Presenter {
    override fun login(userName: String, vCode: String) {
        (mModel as TestModel).login(LoginBO(userName,vCode))

            .let {
                Log.d("请求结果", it.getCompleted().body().toString())
                getView().showResults( it.getCompleted().body().toString())
            }
    }

    override fun createModel(): TestContorl.Model {
        return TestModel()
    }
}