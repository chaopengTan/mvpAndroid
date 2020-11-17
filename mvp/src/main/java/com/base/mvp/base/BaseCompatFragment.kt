package com.base.mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import com.base.mvp.R
import com.base.mvp.view.manager.StateLayoutManager


abstract class BaseCompatFragment: Fragment() {
    protected var mStatusLayoutManager: StateLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_view,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initBaseView(view: View) {
        if (mStatusLayoutManager != null) {
            val ll_main = view.findViewById(R.id.ll_main) as LinearLayout
            ll_main.addView(mStatusLayoutManager!!.getRootLayout())

        }
    }

    protected fun showContent() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showContent()
        }
    }

    protected fun showDataEmpty() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showEmptyData()
        }
    }



    protected fun showNetWorkError() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager!!.showNetWorkError()
        }
    }

    protected abstract fun initStatusLayout(): StateLayoutManager

    protected abstract fun initView()
}