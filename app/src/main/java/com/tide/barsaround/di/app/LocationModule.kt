package com.tide.barsaround.di.app

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.tide.barsaround.helpers.LocationTrackerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    fun provideLocationRequest() = LocationRequest.create().apply {
        interval = 5000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @Provides
    @Singleton
    fun providesLocationTrackerImpl(context: Context, locationRequest: LocationRequest) =
        LocationTrackerImpl(context, locationRequest)
}
