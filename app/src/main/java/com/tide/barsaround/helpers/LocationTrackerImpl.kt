package com.tide.barsaround.helpers

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LocationTrackerImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val locationPermissionImpl: LocationPermissionImpl
) : LocationTracker, LocationPermission by locationPermissionImpl {

    override fun startLocationUpdates(locationCallback: LocationCallback) {
        if (checkLocationPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun stopLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
