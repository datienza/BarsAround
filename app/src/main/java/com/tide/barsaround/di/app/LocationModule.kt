package com.tide.barsaround.di.app

import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides

private const val NUM_LOCATION_UPDATES = 1 // get location of the user just once
@Module
class LocationModule {

    @Provides
    fun provideLocationRequest() = LocationRequest.create().apply {
        numUpdates = NUM_LOCATION_UPDATES
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
}
