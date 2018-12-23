package com.tide.barsaround.data.model

import com.tide.barsaround.calculateDistanceBtwPoints
import android.location.Location

class Bar() {

    var name: String? = null
    var lng: Double = 0.0
    var lat: Double = 0.0
    var distance: String? = null

    constructor(result: Result) : this() {
        name = result.name
        lng = result.geometry.location.lng
        lat = result.geometry.location.lat
    }

    fun setDistanceFromLocation(location: Location?) {
        location?.let {
            val barLocation = Location("").apply {
                latitude = this@Bar.lat
                longitude = this@Bar.lng
            }
            distance = calculateDistanceBtwPoints(it, barLocation)
        }
    }
}
