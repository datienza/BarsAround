package com.tide.barsaround.di.app

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val NUM_LOCATION_UPDATES = 1 // get location of the user just once
@Module
class LocationModule {

    @Provides
    @Singleton
    fun provideLocationRequest() = LocationRequest.create().apply {
        numUpdates = NUM_LOCATION_UPDATES
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(context: Context) =
        LocationServices.getFusedLocationProviderClient(context)
}
