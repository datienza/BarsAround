package com.tide.barsaround

import android.location.Location
import com.tide.barsaround.extensions.readableDistanceFromMeters

fun calculateDistanceBtwPoints(location1: Location, location2: Location) =
    location1.distanceTo(location2).toInt().readableDistanceFromMeters()
