package com.tide.barsaround.ui

import com.tide.barsaround.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.tide.barsaround.di.fragment.FragmentComponent
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.FragmentModule
import com.tide.barsaround.di.fragment.FragmentScope
import com.tide.barsaround.helpers.LocationTrackerImpl
import com.tide.barsaround.presenters.MapFragmentPresenter
import com.tide.barsaround.repository.NearbyRepository
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.Scheduler
import javax.inject.Named

@FragmentScope
@Subcomponent(modules = [MapFragmentComponent.MapsFragmentModule::class])
interface MapFragmentComponent : FragmentComponent<MapFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<MapsFragmentModule, MapFragmentComponent>

    @Module
    class MapsFragmentModule(fragment: MapFragment) : FragmentModule<MapFragment>(fragment) {

        @Provides
        fun provideMapsFragmentPresenter(
            nearbyRepository: NearbyRepository,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler,
            locationTrackerImpl: LocationTrackerImpl
        ) = MapFragmentPresenter(nearbyRepository, uiScheduler, locationTrackerImpl)
    }
}
