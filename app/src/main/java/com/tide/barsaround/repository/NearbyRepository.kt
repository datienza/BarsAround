package com.tide.barsaround.repository

import com.tide.barsaround.data.remote.NearByRemoteDataSource

const val BAR_TYPE = "bar"
const val RADIUS = 1000

class NearbyRepository(private val remoteDataSource: NearByRemoteDataSource) {

    fun getNearbyPlaces(type: String, location: String, radius: Int) =
        remoteDataSource.getNearByPlaces(type, location, radius)
}
