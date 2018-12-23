package com.tide.barsaround.presenters.common

import com.tide.barsaround.contracts.common.BaseListContract
import java.util.Collections

abstract class BaseListPresenter<D, T : BaseListContract.BaseListView<D>> : BasePresenter<T>() {

    fun showLoadingOnStart() {
        (mvpView as BaseListContract.BaseListView<D>).hideEmptyResultView()
        (mvpView as BaseListContract.BaseListView<D>).hideList()
        (mvpView as BaseListContract.BaseListView<D>).showProgressBar()
    }

    abstract fun loadData()

    fun hideSwipeRefreshingView(isSpinnerApplicable: Boolean) {
        if (isSpinnerApplicable) {
            (mvpView as BaseListContract.BaseListView<*>).hideSwipeRefreshingView()
        }
    }

    fun displayData(data: List<D>) {
        (mvpView as BaseListContract.BaseListView<*>).hideProgressBar()
        if (!data.isEmpty()) {
            this.showResult(data)
        } else {
            this.showEmptyResult()
        }
    }

    protected fun showResult(data: List<D>) {
        (mvpView as BaseListContract.BaseListView<D>).hideEmptyResultView()
        (mvpView as BaseListContract.BaseListView<D>).populateList(data)
    }

    protected fun showEmptyResult() {
        (mvpView as BaseListContract.BaseListView<D>).hideList()
        (mvpView as BaseListContract.BaseListView<D>).showEmptyResultView()
    }

    fun sortData(dataList: List<D>, comparator: Comparator<D>): List<D> {
        val data = ArrayList(dataList)
        Collections.sort(data, comparator)
        return data
    }
}
