package com.base.mvp.abs

import com.base.mvp.base.IBasePresenter
import android.view.Gravity
import android.widget.Toast
import android.text.TextUtils
import com.base.mvp.view.manager.StateLayoutManager
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.base.mvp.R
import com.base.mvp.view.manager.OnNetworkListener
import com.base.mvp.view.manager.OnRetryListener
import org.greenrobot.eventbus.EventBus


abstract class MvpFragmentImp<P : IBasePresenter<AbsMVPCompatFragment<P>>> : AbsMVPCompatFragment<P>() {
    protected lateinit var TAG: String
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TAG = this.javaClass.simpleName
        if (registerEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        initView()
        initData(arguments!!)
    }

    protected fun registerEventBus(): Boolean {
        return false
    }


    override fun initStatusLayout(): StateLayoutManager {
        //这个配置,可以直接在base中构建.默认配置.当有需要的时候,子类可以重新new config覆盖
        return StateLayoutManager.newBuilder(context!!)
            .contentView(getLayoutId())
            .emptyDataView(getEmptyLayout())
            .errorView(getErrorLayout())
            .loadingView(getLoadingLayout())
            .netWorkErrorView(getNetWorkLayout())
            .onRetryListener(object : OnRetryListener {
                override fun onRetry() {
                    onRefreshClick()
                }
            })
            .onNetworkListener(object : OnNetworkListener {
                override fun onNetwork() {
                    onNetworkClick()
                }
            })

            .build()
    }

    protected fun getEmptyLayout(): Int {
        return R.layout.view_emptydata
    }

    protected fun getErrorLayout(): Int {
        return R.layout.view_error
    }

    protected fun getLoadingLayout(): Int {
        return R.layout.view_loading
    }

    protected fun getNetWorkLayout(): Int {
        return R.layout.view_networkerror
    }

    abstract fun getLayoutId(): Int

    fun showToast(msg: Int) {
        Toast.makeText(context, "" + resources.getString(msg), Toast.LENGTH_SHORT).show()
    }

    override fun showToast(msg: String) {
        if (TextUtils.isEmpty(msg) || msg == "null") {
            return
        }
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show()
    }


    override fun showToast(view: View) {
        val toast = Toast(context)
        toast.view = view
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

}