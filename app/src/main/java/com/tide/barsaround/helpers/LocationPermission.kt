package com.tide.barsaround.helpers

interface LocationPermission {
    fun checkLocationPermission(): Boolean
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}
