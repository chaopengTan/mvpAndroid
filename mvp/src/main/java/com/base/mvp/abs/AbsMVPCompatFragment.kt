package com.base.mvp.abs

import com.base.mvp.base.BaseCompatFragment
import com.base.mvp.base.IBasePresenter
import com.base.mvp.base.IBaseView
import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.view.View
import com.base.mvp.R


abstract class AbsMVPCompatFragment <P : IBasePresenter<AbsMVPCompatFragment<P>>> : BaseCompatFragment(), IBaseView {
    protected var mPresenter: P? = null

    override fun getAct(): Activity {
        return this.activity!!
    }

    override fun startToActivity(intent: Intent) {
        if (intent == null) {
            throw NullPointerException(getString(R.string.text_intent_null_error))
        }
        startActivity(intent)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.onViewAttached(AbsMVPCompatFragment@this)
            //设置owner
            mPresenter!!.setLifecycleOwner(this)
            //p实现了owner接口
            lifecycle.addObserver(mPresenter!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPresenter != null) {
            mPresenter!!.onViewDetached()
        }
    }


    /**
     * 子类必须实现该方法获取Presenter
     *
     * @return 返回该子类所需的Presenter
     */
    protected abstract fun createPresenter(): P

    /**
     * 子类初始化页面方法
     */
    override fun initView() {

    }

    /**
     * 子类初始化数据方法
     *
     * @param bundle 传入的数据，可能为空
     */
    protected fun initData(bundle: Bundle) {

    }

    override fun showNoNetworkView() {
        showNetWorkError()
    }


    override fun showEmptyData() {
        showDataEmpty()
    }

    fun showContentView() {
        showContent()
    }

    protected fun onRefreshClick() {
        //显示错误页面后,点击刷新会触发此方法,子类需要可以重写,实现刷新逻辑
    }

    protected fun onNetworkClick() {
        //无网络显示页面,点击会触发此方法,子类需要可以重写,实现无网络逻辑

    }
}