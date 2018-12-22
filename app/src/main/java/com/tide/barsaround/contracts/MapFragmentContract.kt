package com.tide.barsaround.contracts

import com.tide.barsaround.data.model.Result
import com.tide.barsaround.ui.common.MvpView

interface MapFragmentContract {
    interface View : MvpView, LocationPermissionContract {
        fun displayData(bars: List<Result>)
    }
}
