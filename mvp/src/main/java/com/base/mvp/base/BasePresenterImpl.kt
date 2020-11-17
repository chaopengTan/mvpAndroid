package com.base.mvp.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference
import java.lang.reflect.Proxy


abstract class BasePresenterImpl <V : IBaseView, M : IBaseModel>: IBasePresenter<V> {
    private var lifecycleOwner: LifecycleOwner? = null

    protected var mModel: M? = null

    private var viewProxy: V? = null


    protected var mViewRef: WeakReference<V>? = null

    protected abstract fun createModel(): M


    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onPause(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onViewAttached(view: V){
        mViewRef = WeakReference(view)
        var interfaces = view::class.java.interfaces
        var found = false
        for (anInterface in interfaces) {
            if (anInterface == IBaseView::class.java) {
                found = true
            }
        }
        //如果没找到,增加base
        if (!found) {
            val infs = interfaces.copyOf(interfaces.size + 1)
            infs[interfaces.size] = IBaseView::class.java
            interfaces = infs
        }
        var proxyInstance =
            Proxy.newProxyInstance(view::class.java.classLoader, interfaces) { proxy, method, args ->
                if (mViewRef == null || mViewRef!!.get() == null) {
                    return@newProxyInstance null
                } else {
                    return@newProxyInstance method.invoke (mViewRef!!.get(), args)
                }
            }
        if(proxyInstance!=null){
            viewProxy = proxyInstance as V
        }
        mModel = createModel()
    }

    override fun onViewDetached() {
        if (isAttach()) {
            mViewRef!!.clear()
            mViewRef = null
        }
        if (mModel != null) {
            mModel = null
        }
    }

    override fun getView(): V {
        return this!!.viewProxy!!
    }

    override fun setLifecycleOwner(owner: LifecycleOwner) {
        lifecycleOwner = owner;
    }


    private fun isAttach(): Boolean {
        return mViewRef != null && mViewRef!!.get() != null
    }

}