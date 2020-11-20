package com.base.mvp.abs

import android.content.Intent
import android.os.Bundle
import android.app.Activity
import androidx.annotation.Nullable
import com.base.mvp.R
import com.base.mvp.base.IBaseView
import com.base.mvp.base.BaseActivity
import com.base.mvp.base.IBasePresenter



abstract class AbsMVPActivity<P : IBasePresenter<V>,V:IBaseView> : BaseActivity(), IBaseView {

    protected var mPresenter: P? = null

    override fun getAct(): Activity {
        return this
    }

    override fun startToActivity(intent: Intent) {
        if (intent == null) {
            throw NullPointerException(getString(R.string.text_intent_null_error))
        }
        startActivity(intent)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.onViewAttached(this as V)
            //设置owner
            mPresenter!!.setLifecycleOwner(this)
            //p实现了owner接口
            lifecycle.addObserver(mPresenter!!)
        }


    }

    override fun isFullScreen(): Boolean {
        //默认不是全屏,如果有需要,子类复写
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
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

    override fun initView() {

    }

    protected fun initData(intent: Intent) {

    }

    override fun showEmptyData() {
        showEmptyDataViwe()
    }

    override fun showNoNetworkView() {
        showNetWorkError()
    }


    fun showContentView() {
        showContent()
    }

    override fun onRefreshClick() {

    }

    override fun onNetworkClick() {

    }
}
