package com.tide.barsaround.di.fragment

import com.tide.barsaround.ui.BarsFragment
import com.tide.barsaround.ui.BarsFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    BarsFragmentComponent::class
])
abstract class MainActivityFragmentBindingModule {
    @Binds
    @IntoMap
    @FragmentKey(BarsFragment::class)
    abstract fun barsFragmentComponentBuilder(impl: BarsFragmentComponent.Builder): FragmentComponentBuilder<*, *>
}
