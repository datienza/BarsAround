package com.tide.barsaround.ui

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.tide.barsaround.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.tide.barsaround.di.fragment.FragmentComponent
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.FragmentModule
import com.tide.barsaround.di.fragment.FragmentScope
import com.tide.barsaround.helpers.LocationPermissionImpl
import com.tide.barsaround.helpers.LocationTrackerImpl
import com.tide.barsaround.presenters.BarsFragmentPresenter
import com.tide.barsaround.repository.NearbyRepository
import com.tide.barsaround.ui.adapters.BarsAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.Scheduler
import javax.inject.Named

@FragmentScope
@Subcomponent(modules = [BarsFragmentComponent.BarsFragmentModule::class])
interface BarsFragmentComponent : FragmentComponent<BarsFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<BarsFragmentModule, BarsFragmentComponent>

    @Module
    class BarsFragmentModule(fragment: BarsFragment) : FragmentModule<BarsFragment>(fragment) {

        @Provides
        fun provideLocationPermission() = LocationPermissionImpl(fragment.context!!, fragment)

        @Provides
        fun providesLocationTrackerImpl(
            context: Context,
            locationRequest: LocationRequest,
            locationPermissionImpl: LocationPermissionImpl
        ) =
            LocationTrackerImpl(context, locationRequest, locationPermissionImpl)

        @Provides
        fun provideBarsFragmentPresenter(
            nearbyRepository: NearbyRepository,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler,
            locationTrackerImpl: LocationTrackerImpl,
            locationPermissionImpl: LocationPermissionImpl
        ) = BarsFragmentPresenter(nearbyRepository, uiScheduler, locationTrackerImpl, locationPermissionImpl)

        @Provides
        fun provideBarsAdapter() = BarsAdapter(fragment.context!!)
    }
}
