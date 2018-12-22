package com.tide.barsaround.contracts

import androidx.annotation.IdRes
import com.tide.barsaround.ui.common.MvpView

interface MainActivityContract {
    interface View : MvpView {
        fun checkSelfLocationPermission(): Int
        fun requestLocationPermission()
        fun isAboveAndroidMarshmallow(): Boolean
        @IdRes fun getNavigationItemMapId(): Int
        @IdRes fun getNavigationItemBarId(): Int
        fun navigateToMapTab()
        fun navigateToBarTab()
        fun notifyTabPermissionGranted()
    }
}
