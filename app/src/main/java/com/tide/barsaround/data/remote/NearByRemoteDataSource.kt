package com.tide.barsaround.data.remote

import com.tide.barsaround.data.model.NearByResponse
import com.tide.barsaround.data.remote.network.NearByApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NearByRemoteDataSource(private val retrofitApi: NearByApi) {

    fun getNearByPlaces(type: String, location: String): Single<NearByResponse> =
        retrofitApi.getNearbyPlaces(type, location)
            .subscribeOn(Schedulers.io())
}
