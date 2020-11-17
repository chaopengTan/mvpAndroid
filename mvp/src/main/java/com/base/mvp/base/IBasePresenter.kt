package com.base.mvp.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent


interface IBasePresenter<V : IBaseView> : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(
        owner: LifecycleOwner,
        event: Lifecycle.Event
    )


    fun onViewAttached(view: V)

    fun onViewDetached()

    fun getView(): V

    fun setLifecycleOwner(owner: LifecycleOwner)
}