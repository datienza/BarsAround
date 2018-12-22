package com.tide.barsaround.presenters

import android.content.pm.PackageManager
import com.tide.barsaround.contracts.MainActivityContract
import com.tide.barsaround.presenters.common.BasePresenter

class MainActivityPresenter : BasePresenter<MainActivityContract.View>() {

    private var navigationItemSelected: Int? = 0

    fun checkLocationPermission() =
        mvpView?.let { view ->
            return if (view.isAboveAndroidMarshmallow() && view.checkSelfLocationPermission() != PackageManager.PERMISSION_GRANTED) {
                view.requestLocationPermission()
                false
            } else {
                true
            }
        } ?: false

    fun notifyLocationPermissionGranted() {
        mvpView?.let { view ->
            when (navigationItemSelected) {
                view.getNavigationItemBarId(),
                view.getNavigationItemMapId() -> view.notifyTabPermissionGranted()
            }
        }
    }

    fun navigationItemSelected(itemId: Int): Boolean {
        mvpView?.let { view ->
            navigationItemSelected = itemId
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
