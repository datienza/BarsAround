package com.tide.barsaround.ui

import com.test.spacex.ui.adapter.products.BarsAdapter
import com.tide.barsaround.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.tide.barsaround.di.fragment.FragmentComponent
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.FragmentModule
import com.tide.barsaround.di.fragment.FragmentScope
import com.tide.barsaround.presenters.BarsFragmentPresenter
import com.tide.barsaround.repository.NearbyRepository
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
        fun provideBarsFragmentPresenter(
            nearbyRepository: NearbyRepository,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler
        ) = BarsFragmentPresenter(nearbyRepository, uiScheduler)

        @Provides
        fun provideBarsAdapter() = BarsAdapter(fragment.context!!)
    }
}
