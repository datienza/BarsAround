package com.tide.barsaround.contracts

import android.location.Location
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.ui.common.MvpView

interface MapFragmentContract {
    interface View : MvpView, LocationPermissionContract.View {
        fun displayData(currentLocation: Location, bars: List<Bar>)
    }
}
