package com.tide.barsaround.presenters.common

import com.tide.barsaround.ui.common.MvpView

interface Presenter<V : MvpView> {
    fun attachView(var1: V)

    fun detachView()
}
