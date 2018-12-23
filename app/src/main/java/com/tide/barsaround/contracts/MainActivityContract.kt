package com.tide.barsaround.contracts

import androidx.annotation.IdRes
import com.tide.barsaround.ui.common.MvpView

interface MainActivityContract {
    interface View : MvpView {
        @IdRes fun getNavigationItemMapId(): Int
        @IdRes fun getNavigationItemBarId(): Int
        fun navigateToMapTab()
        fun navigateToBarTab()
    }
}
