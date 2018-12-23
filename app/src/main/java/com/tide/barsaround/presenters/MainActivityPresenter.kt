package com.tide.barsaround.presenters

import com.tide.barsaround.contracts.MainActivityContract
import com.tide.barsaround.presenters.common.BasePresenter

class MainActivityPresenter : BasePresenter<MainActivityContract.View>() {

    private var lastSelectedItemId: Int = 0

    fun navigationItemSelected(itemId: Int): Boolean {
        mvpView?.let { view ->
            if (itemId == lastSelectedItemId) {
                return true
            }
            lastSelectedItemId = itemId
            return when (itemId) {
                view.getNavigationItemBarId() -> {
                    view.navigateToBarTab()
                    true
                }
                else -> {
                    view.navigateToMapTab()
                    true
                }
            }
        }
        return false
    }
}
