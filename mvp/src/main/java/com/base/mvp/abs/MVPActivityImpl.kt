package com.base.mvp.abs

import org.greenrobot.eventbus.EventBus
import android.view.Gravity
import android.widget.Toast
import android.text.TextUtils
import com.base.mvp.view.manager.StateLayoutManager
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.base.mvp.R
import com.base.mvp.base.IBasePresenter
import com.base.mvp.base.IBaseView
import com.base.mvp.view.manager.OnNetworkListener
import com.base.mvp.view.manager.OnRetryListener


abstract class MVPActivityImpl<P : IBasePresenter<V>,V:IBaseView> : AbsMVPActivity<P,V>() {

    /**
     * 子类如果需要更改空页面布局,直接复写方法,下面几个方法同理
     *
     * @return getEmptyLayout
     */
    protected val emptyLayout: Int
        get() = R.layout.view_emptydata

    protected val errorLayout: Int
        get() = R.layout.view_error

    protected val loadingLayout: Int
        get() = R.layout.view_loading

    protected val netWorkLayout: Int
        get() = R.layout.view_networkerror

    /**
     * 我们页面布局
     *
     * @return
     */
    abstract val layoutId: Int


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (registerEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        initView()
        initData(intent)
    }

    protected fun registerEventBus(): Boolean {
        return false
    }

    override fun initStatusLayout(): StateLayoutManager {
        //这个配置,可以直接在base中构建.默认配置.当有需要的时候,子类可以重新new config覆盖
        mStatusLayoutManager = StateLayoutManager.newBuilder(this)
            .contentView(layoutId)
            .emptyDataView(emptyLayout)
            //也可以是app模块自己定制
            .errorView(errorLayout)
            .loadingView(loadingLayout)
            .netWorkErrorView(netWorkLayout)
            .onRetryListener(object : OnRetryListener{
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
        mStatusLayoutManager!!.showContent()
        return mStatusLayoutManager as StateLayoutManager
    }




    fun showToast(msg: Int) {
        Toast.makeText(this, "" + resources.getString(msg), Toast.LENGTH_SHORT).show()
    }

    override fun showToast(msg: String) {
        if (TextUtils.isEmpty(msg) || msg == "null") {
            return
        }
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(view: View) {
        val toast = Toast(this)
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
