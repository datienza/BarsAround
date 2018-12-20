package com.tide.barsaround.contracts.common

import com.tide.barsaround.ui.common.MvpView

interface BaseListContract {
    interface BaseListView<J> : MvpView {
        fun hideSwipeRefreshingView()

        fun showEmptyResultView()

        fun hideEmptyResultView()

        fun showEmptySearchView()

        fun hideEmptySearchView()

        fun showProgressBar()

        fun hideProgressBar()

        fun populateList(var1: List<J>)

        fun hideList()
    }
}
