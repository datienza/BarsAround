package com.tide.barsaround.repository

import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.data.remote.NearByRemoteDataSource

const val BAR_TYPE = "bar"

class NearbyRepository(private val remoteDataSource: NearByRemoteDataSource) {

    fun getNearbyPlaces(type: String, location: String) =
        remoteDataSource.getNearByPlaces(type, location)
            .map { nearByResponse ->
                nearByResponse.results?.map { Bar(it) }
            }
}
