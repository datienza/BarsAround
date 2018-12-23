package com.tide.barsaround.helpers

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationTrackerImpl(
    private val context: Context,
    private val locationRequest: LocationRequest,
    private val locationPermissionImpl: LocationPermissionImpl
) : LocationTracker, LocationPermission by locationPermissionImpl {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun startLocationUpdates(locationCallback: LocationCallback) {
        if (checkLocationPermission()) {
            if (fusedLocationClient == null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            }

            fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun stopLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }
}
