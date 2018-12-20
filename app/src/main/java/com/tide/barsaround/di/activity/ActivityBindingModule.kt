package com.tide.barsaround.di.activity

import com.tide.barsaround.ui.MainActivity
import com.tide.barsaround.ui.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivityComponent::class])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun mainActivityComponentBuilder(impl: MainActivityComponent.Builder): ActivityComponentBuilder<*, *>
}
