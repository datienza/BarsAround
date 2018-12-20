package com.tide.barsaround.data.remote.network

import com.tide.barsaround.data.model.NearByResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val PLACES_API_KEY = "AIzaSyCe41lFznZNN-J1ku4f2nzRSyC7SDTw9N0"

interface NearByApi {

    @GET("nearbysearch/json?sensor=true&key=$PLACES_API_KEY")
    fun getNearbyPlaces(@Query("type") type: String, @Query("location") location: String, @Query("radius") radius: Int): Single<NearByResponse>
}
