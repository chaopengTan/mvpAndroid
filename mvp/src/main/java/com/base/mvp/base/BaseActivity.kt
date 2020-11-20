package com.base.mvp.base

import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import com.base.mvp.view.manager.StateLayoutManager
import android.view.WindowManager
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.base.mvp.R


abstract class BaseActivity : AppCompatActivity() {
    //方便在父类控制状态.子类中可能会调用管理器的方法.
    protected var mStatusLayoutManager: StateLayoutManager? = null


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullScreen()) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        //所有act的父布局
        setContentView(R.layout.activity_base_view)
        mStatusLayoutManager = initStatusLayout()
        initBaseView()
    }

    protected abstract fun isFullScreen(): Boolean

    protected abstract fun initStatusLayout(): StateLayoutManager

    protected abstract fun initView()

    protected abstract fun onRefreshClick()

    protected abstract fun onNetworkClick()

    private fun initBaseView() {
        if (mStatusLayoutManager != null) {
            val ll_main = findViewById<View>(R.id.ll_main) as LinearLayout
            ll_main.addView(mStatusLayoutManager!!.getRootLayout())
        }
    }

    protected fun showContent() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showContent()
        }
    }

    protected fun showEmptyDataViwe() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showEmptyData()
        }
    }


    protected fun showNetWorkError() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showNetWorkError()
        }
    }

}