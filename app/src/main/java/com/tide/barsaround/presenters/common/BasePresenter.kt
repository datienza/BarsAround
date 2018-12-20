package com.tide.barsaround.presenters.common

import com.tide.barsaround.ui.common.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpView> : Presenter<T> {

    protected var mvpView: T? = null

    protected var compositeDisposable: CompositeDisposable? = null

    val isViewAttached: Boolean
        get() = this.mvpView != null

    val isDisposableReady: Boolean
        get() = this.compositeDisposable != null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) {
            throw BasePresenter.MvpViewNotAttachedException()
        }
    }

    fun addDisposable(newDisposable: Disposable?) {
        if (newDisposable != null && compositeDisposable != null) {
            compositeDisposable?.add(newDisposable)
        } else if (newDisposable != null) {
            compositeDisposable = CompositeDisposable()
            compositeDisposable?.add(newDisposable)
        }
    }

    fun destroyAllDisposables() {
        if (compositeDisposable != null) {
            compositeDisposable?.dispose()
            compositeDisposable = null
        }
    }

    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
