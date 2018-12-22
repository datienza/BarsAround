package com.tide.barsaround.di.fragment

import com.tide.barsaround.ui.BarsFragment
import com.tide.barsaround.ui.BarsFragmentComponent
import com.tide.barsaround.ui.MapFragment
import com.tide.barsaround.ui.MapFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    BarsFragmentComponent::class,
    MapFragmentComponent::class
])
abstract class MainActivityFragmentBindingModule {
    @Binds
    @IntoMap
    @FragmentKey(BarsFragment::class)
    abstract fun barsFragmentComponentBuilder(impl: BarsFragmentComponent.Builder): FragmentComponentBuilder<*, *>

    @Binds
    @IntoMap
    @FragmentKey(MapFragment::class)
    abstract fun mapFragmentComponentBuilder(impl: MapFragmentComponent.Builder): FragmentComponentBuilder<*, *>
}
