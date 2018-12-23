package com.tide.barsaround.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tide.barsaround.contracts.LocationPermissionContract

const val MY_PERMISSIONS_REQUEST_LOCATION = 9

class LocationPermissionImpl(private val context: Context, private val view: LocationPermissionContract.View) :
    LocationPermission {

    override fun checkLocationPermission(): Boolean {
        if (isAboveAndroidMarshmallow() &&
            getSelfLocationPermission() != PackageManager.PERMISSION_GRANTED
        ) {
            view.requestLocationPermission()
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    getSelfLocationPermission() == PackageManager.PERMISSION_GRANTED
                ) {
                    view.onLocationPermissionGranted()
                } else {
                    Toast.makeText(
                        context,
                        "Location Permission has been denied, can not search the places you want.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    private fun getSelfLocationPermission() =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun isAboveAndroidMarshmallow() = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}
