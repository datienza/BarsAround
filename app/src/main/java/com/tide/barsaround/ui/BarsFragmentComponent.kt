package com.tide.barsaround.ui

import com.test.spacex.ui.adapter.products.BarsAdapter
import com.tide.barsaround.di.fragment.FragmentComponent
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.FragmentModule
import com.tide.barsaround.di.fragment.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [BarsFragmentComponent.BarsFragmentModule::class])
interface BarsFragmentComponent : FragmentComponent<BarsFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<BarsFragmentModule, BarsFragmentComponent>

    @Module
    class BarsFragmentModule(fragment: BarsFragment) : FragmentModule<BarsFragment>(fragment) {

        @Provides
        fun provideBarsAdapter() = BarsAdapter(fragment.context!!)
    }
}
