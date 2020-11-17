package com.base.mvp.base

import android.content.Intent
import android.app.Activity
import android.view.View


interface IBaseView {
    fun showNoNetworkView()

    fun showEmptyData()

    fun getAct(): Activity

    fun startToActivity(intent: Intent)

    fun showToast(msg: String)

    fun showToast(view: View)
}