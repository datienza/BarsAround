package com.tide.barsaround.helpers

import com.google.android.gms.location.LocationCallback

interface LocationTracker {
    fun startLocationUpdates(locationCallback: LocationCallback)
    fun stopLocationUpdates(locationCallback: LocationCallback)
}
