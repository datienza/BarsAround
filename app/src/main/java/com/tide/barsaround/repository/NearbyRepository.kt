package com.tide.barsaround.repository

import com.tide.barsaround.data.remote.NearByRemoteDataSource

const val BAR_TYPE = "bar"
const val RADIUS = 2000

class NearbyRepository(private val remoteDataSource: NearByRemoteDataSource) {

    fun getNearbyPlaces(type: String, location: String) =
        remoteDataSource.getNearByPlaces(type, location)
}
