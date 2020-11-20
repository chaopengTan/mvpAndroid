package com.base.mvp.view.manager

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import android.view.ViewStub
import android.view.ViewGroup
import com.base.mvp.view.AbsViewStubLayout
import com.base.mvp.view.StateFrameLayout



class StateLayoutManager constructor(builder: Builder){

    var context: Context? = null
    var netWorkErrorVs: ViewStub? = null
    var netWorkErrorRetryViewId: Int = 0
    var emptyDataVs: ViewStub? = null
    var emptyDataRetryViewId: Int = 0
    var errorVs: ViewStub? = null
    var errorRetryViewId: Int = 0
    var loadingLayoutResId: Int = 0
    var contentLayoutResId: Int = 0
    var retryViewId: Int = 0
    var emptyDataIconImageId: Int = 0
    var emptyDataTextTipId: Int = 0
    var errorIconImageId: Int = 0
    var errorTextTipId: Int = 0
    var emptyDataLayout: AbsViewStubLayout? = null

    lateinit var rootFrameLayout: StateFrameLayout
    var onRetryListener: OnRetryListener? = null
    var onNetworkListener: OnNetworkListener? = null

    companion object{
        fun newBuilder(context: Context): Builder {
            return Builder(context)
        }
    }

    init {
        this.context = builder.context
        this.loadingLayoutResId = builder.loadingLayoutResId
        this.netWorkErrorVs = builder.netWorkErrorVs
        this.netWorkErrorRetryViewId = builder.netWorkErrorRetryViewId
        this.emptyDataVs = builder.emptyDataVs
        this.emptyDataRetryViewId = builder.emptyDataRetryViewId
        this.errorVs = builder.errorVs
        this.errorRetryViewId = builder.errorRetryViewId
        this.contentLayoutResId = builder.contentLayoutResId
        this.retryViewId = builder.retryViewId
        this.onRetryListener = builder.onRetryListener
        this.onNetworkListener = builder.onNetworkListener
        this.emptyDataIconImageId = builder.emptyDataIconImageId
        this.emptyDataTextTipId = builder.emptyDataTextTipId
        this.errorIconImageId = builder.errorIconImageId
        this.errorTextTipId = builder.errorTextTipId
        this.emptyDataLayout = builder.emptyDataLayout

        //创建帧布局
        rootFrameLayout = StateFrameLayout(this.context!!)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        rootFrameLayout.layoutParams = layoutParams

        //设置状态管理器
        rootFrameLayout.setStatusLayoutManager(this)
    }

    /**
     * 显示内容
     */
    fun showContent() {
        rootFrameLayout.showContent()
    }

    /**
     * 显示空数据
     */
    fun showEmptyData(iconImage: Int, textTip: String) {
        rootFrameLayout.showEmptyData(iconImage, textTip)
    }


    /**
     * 显示空数据
     */
    fun showEmptyData() {
        showEmptyData(0, "")
    }

    /**
     * 显示空数据
     */
    fun showLayoutEmptyData(vararg objects: Any) {
        rootFrameLayout.showLayoutEmptyData(*objects)
    }

    /**
     * 显示网络异常
     */
    fun showNetWorkError() {
        rootFrameLayout.showNetWorkError()
    }


    /**
     * 得到root 布局
     */
    fun getRootLayout(): View {
        return rootFrameLayout
    }

    class Builder internal constructor( val context: Context) {
         var loadingLayoutResId: Int = 0
         var contentLayoutResId: Int = 0
         var netWorkErrorVs: ViewStub? = null
         var netWorkErrorRetryViewId: Int = 0
         var emptyDataVs: ViewStub? = null
         var emptyDataRetryViewId: Int = 0
         var errorVs: ViewStub? = null
         var errorRetryViewId: Int = 0
         var retryViewId: Int = 0
         var emptyDataIconImageId: Int = 0
         var emptyDataTextTipId: Int = 0
         var errorIconImageId: Int = 0
         var errorTextTipId: Int = 0
         var emptyDataLayout: AbsViewStubLayout? = null
         var onRetryListener: OnRetryListener? = null
         var onNetworkListener: OnNetworkListener? = null

        /**
         * 自定义加载布局
         */
        fun loadingView(@LayoutRes loadingLayoutResId: Int): Builder {
            this.loadingLayoutResId = loadingLayoutResId
            return this
        }

        /**
         * 自定义网络错误布局
         */
        fun netWorkErrorView(@LayoutRes newWorkErrorId: Int): Builder {
            netWorkErrorVs = ViewStub(context)
            netWorkErrorVs!!.layoutResource = newWorkErrorId
            return this
        }

        /**
         * 自定义加载空数据布局
         */
        fun emptyDataView(@LayoutRes noDataViewId: Int): Builder {
            emptyDataVs = ViewStub(context)
            emptyDataVs!!.layoutResource = noDataViewId
            return this
        }

        /**
         * 自定义加载错误布局
         */
        fun errorView(@LayoutRes errorViewId: Int): Builder {
            errorVs = ViewStub(context)
            errorVs!!.layoutResource = errorViewId
            return this
        }

        /**
         * 自定义加载内容正常布局
         */
        fun contentView(@LayoutRes contentLayoutResId: Int): Builder {
            this.contentLayoutResId = contentLayoutResId
            return this
        }



        /**
         * 自定义空数据布局
         * @param emptyDataLayout              emptyDataLayout
         * @return
         */
        fun emptyDataLayout(emptyDataLayout: AbsViewStubLayout): Builder {
            this.emptyDataLayout = emptyDataLayout
            this.emptyDataVs = emptyDataLayout.getLayoutVs()
            return this
        }

        fun netWorkErrorRetryViewId(@LayoutRes netWorkErrorRetryViewId: Int): Builder {
            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId
            return this
        }

        fun emptyDataRetryViewId(@LayoutRes emptyDataRetryViewId: Int): Builder {
            this.emptyDataRetryViewId = emptyDataRetryViewId
            return this
        }

        fun errorRetryViewId(@LayoutRes errorRetryViewId: Int): Builder {
            this.errorRetryViewId = errorRetryViewId
            return this
        }

        fun retryViewId(@LayoutRes retryViewId: Int): Builder {
            this.retryViewId = retryViewId
            return this
        }

        fun emptyDataIconImageId(@LayoutRes emptyDataIconImageId: Int): Builder {
            this.emptyDataIconImageId = emptyDataIconImageId
            return this
        }

        fun emptyDataTextTipId(@LayoutRes emptyDataTextTipId: Int): Builder {
            this.emptyDataTextTipId = emptyDataTextTipId
            return this
        }

        fun errorIconImageId(@LayoutRes errorIconImageId: Int): Builder {
            this.errorIconImageId = errorIconImageId
            return this
        }

        fun errorTextTipId(@LayoutRes errorTextTipId: Int): Builder {
            this.errorTextTipId = errorTextTipId
            return this
        }


        /**
         * 为重试加载按钮的监听事件
         * @param onRetryListener           listener
         * @return
         */
        fun onRetryListener(onRetryListener: OnRetryListener): Builder {
            this.onRetryListener = onRetryListener
            return this
        }

        /**
         * 为重试加载按钮的监听事件
         * @param onNetworkListener           listener
         * @return
         */
        fun onNetworkListener(onNetworkListener: OnNetworkListener): Builder {
            this.onNetworkListener = onNetworkListener
            return this
        }


        /**
         * 创建对象
         * @return
         */
        fun build(): StateLayoutManager {
            return StateLayoutManager(this)
        }
    }


}