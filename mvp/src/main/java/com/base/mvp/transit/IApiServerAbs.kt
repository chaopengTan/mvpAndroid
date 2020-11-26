package com.base.mvp.transit

abstract class IApiServerAbs<T> {
    abstract fun getServer():Class<T>
}