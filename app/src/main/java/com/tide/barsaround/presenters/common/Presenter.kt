package com.tide.barsaround.presenters.common

import com.tide.barsaround.ui.common.MvpView

interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)

    fun detachView()
}
