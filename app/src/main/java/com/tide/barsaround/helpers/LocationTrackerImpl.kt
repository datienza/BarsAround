package com.tide.barsaround.helpers

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationTrackerImpl(private val context: Context, private val locationRequest: LocationRequest) : LocationTracker {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates(locationCallback: LocationCallback) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun stopLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }
}
