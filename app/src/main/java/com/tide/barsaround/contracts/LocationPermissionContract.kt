package com.tide.barsaround.contracts

import com.tide.barsaround.ui.common.MvpView

interface LocationPermissionContract {
    interface View : MvpView {
        fun onLocationPermissionGranted()
        fun requestLocationPermission()
    }
}
