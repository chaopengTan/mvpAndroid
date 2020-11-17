package com.base.mvp.view

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import com.base.mvp.view.manager.StateLayoutManager

class StateFrameLayout(context: Context) : FrameLayout(context) {
    /**
     * 内容id
     */
    val LAYOUT_CONTENT_ID = 2

    /**
     * 网络异常id
     */
    val LAYOUT_NETWORK_ERROR_ID = 4

    /**
     * 空数据id
     */
    val LAYOUT_EMPTY_DATA_ID = 5

    private val layoutSparseArray = SparseArray<View>()
    /**
     * 布局管理器
     */
    private var mStatusLayoutManager: StateLayoutManager? = null

    constructor(context: Context, attrs: AttributeSet) : this(context)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context)

    /**
     * 设置状态管理者manager
     * 该manager主要的操作是设置不同状态view以及设置属性
     * 而该帧布局主要操作是显示和隐藏视图，用帧布局可以较少一层视图层级
     * 这样操作是利用了面向对象中封装类尽量保持类的单一职责，一个类尽量只做一件事情
     * @param statusLayoutManager               manager
     */
    fun setStatusLayoutManager(statusLayoutManager: StateLayoutManager) {
        mStatusLayoutManager = statusLayoutManager
        //添加所有的布局到帧布局
        //布局id从管理器获得
        addAllLayoutToRootLayout()
    }

    /**
     * 添加所有不同状态布局到帧布局中
     */
    private fun addAllLayoutToRootLayout() {
        //空数据布局
        if (mStatusLayoutManager!!.emptyDataVs != null) {
            addView(mStatusLayoutManager!!.emptyDataVs)
        }
        //错误布局
        if (mStatusLayoutManager!!.errorVs != null) {
            addView(mStatusLayoutManager!!.errorVs)
        }
        //网络错误布局
        if (mStatusLayoutManager!!.netWorkErrorVs != null) {
            addView(mStatusLayoutManager!!.netWorkErrorVs)
        }
    }

    /**
     * 把布局使用LayoutInflater加载,之后,保存到SparseArray中.key是固定好的几个业务分类
     * 然后,把这个加载出来的view 添加到帧布局中.
     * @param layoutResId
     * @param id
     */
    private fun addLayoutResId(@LayoutRes layoutResId: Int, id: Int) {
        val resView = LayoutInflater.from(mStatusLayoutManager!!.context).inflate(layoutResId, null)
        layoutSparseArray.put(id, resView)
        addView(resView)
    }


    /**
     * 显示内容
     */
    fun showContent() {
        if (layoutSparseArray.get(LAYOUT_CONTENT_ID) != null) {
            showHideViewById(LAYOUT_CONTENT_ID)
        }
    }

    /**
     * 显示空数据
     */
    fun showEmptyData(iconImage: Int, textTip: String) {
        if (inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            showHideViewById(LAYOUT_EMPTY_DATA_ID)
            emptyDataViewAddData(iconImage, textTip)
        }
    }

    /**
     * 显示网络异常
     */
    fun showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID)) {
            showHideViewById(LAYOUT_NETWORK_ERROR_ID)
        }
    }

    /**
     * 空数据并且设置页面简单数据
     * @param iconImage                 空页面图片
     * @param textTip                   文字
     */
    private fun emptyDataViewAddData(iconImage: Int, textTip: String) {
        if (iconImage == 0 && TextUtils.isEmpty(textTip)) {
            return
        }
        val emptyDataView = layoutSparseArray.get(LAYOUT_EMPTY_DATA_ID)
        val iconImageView = emptyDataView.findViewById<View>(mStatusLayoutManager!!.emptyDataIconImageId)
        val textView = emptyDataView.findViewById<View>(mStatusLayoutManager!!.emptyDataTextTipId)
        if (iconImageView != null && iconImageView is ImageView) {
            (iconImageView as ImageView).setImageResource(iconImage)
        }

        if (textView != null && textView is TextView) {
            (textView as TextView).text = textTip
        }
    }

    /**
     * 展示空页面
     * @param objects                   object
     */
    fun showLayoutEmptyData(vararg objects: Any) {
        if (inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            showHideViewById(LAYOUT_EMPTY_DATA_ID)

            val emptyDataLayout = mStatusLayoutManager!!.emptyDataLayout
            if (emptyDataLayout != null) {
                emptyDataLayout!!.setData(objects)
            }
        }
    }



    /**
     * 根据ID显示隐藏布局
     * @param id                    id值 布局的类型
     */
    private fun showHideViewById(id: Int) {
        //这个需要遍历集合中数据，然后切换显示和隐藏
        for (i in 0 until layoutSparseArray.size()) {
            val key = layoutSparseArray.keyAt(i)
            val valueView = layoutSparseArray.valueAt(i)
            //显示该view
            if (key == id) {
                //显示该视图
                valueView.setVisibility(View.VISIBLE)
                if (mStatusLayoutManager!!.onShowHideViewListener != null) {
                    mStatusLayoutManager!!.onShowHideViewListener.onShowView(valueView, key)
                }
            } else {
                //隐藏该视图
                if (valueView.getVisibility() !== View.GONE) {
                    valueView.setVisibility(View.GONE)
                    if (mStatusLayoutManager!!.onShowHideViewListener != null) {
                        mStatusLayoutManager!!.onShowHideViewListener.onHideView(valueView, key)
                    }
                }
            }
        }
    }


    /**
     * 主要是显示ViewStub布局，比如网络异常，加载异常以及空数据等页面
     * 注意该方法中只有当切换到这些页面的时候，才会将ViewStub视图给inflate出来，之后才会走视图绘制的三大流程
     * 方法里面通过id判断来执行不同的代码，首先判断ViewStub是否为空，如果为空就代表没有添加这个View就返回false，
     * 不为空就加载View并且添加到集合当中，然后调用showHideViewById方法显示隐藏View，
     * retryLoad方法是给重试按钮添加事件
     * @param id                        布局id
     * @return                          是否inflate出视图
     */
    private fun inflateLayout(id: Int): Boolean {
        var isShow = true
        if (layoutSparseArray.get(id) != null) {
            //如果这个id对应的布局已经被加载出来,可以直接返回显示.
            return isShow
        }
        when (id) {
            //网络异常
            LAYOUT_NETWORK_ERROR_ID -> if (mStatusLayoutManager!!.netWorkErrorVs != null) {
                val view = mStatusLayoutManager!!.netWorkErrorVs!!.inflate()
                view.setOnClickListener { mStatusLayoutManager!!.onNetworkListener.onNetwork() }
                layoutSparseArray.put(id, view)
                isShow = true
            } else {
                isShow = false
            }

            //空数据
            LAYOUT_EMPTY_DATA_ID -> if (mStatusLayoutManager!!.emptyDataVs != null) {
                val view = mStatusLayoutManager!!.emptyDataVs!!.inflate()
                if (mStatusLayoutManager!!.emptyDataLayout != null) {
                    mStatusLayoutManager!!.emptyDataLayout.setView(view)
                }
                view.setOnClickListener { mStatusLayoutManager!!.onRetryListener.onRetry() }
                layoutSparseArray.put(id, view)
                isShow = true
            } else {
                isShow = false
            }
            else -> {
            }
        }
        return isShow
    }
}