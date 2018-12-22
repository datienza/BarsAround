package com.tide.barsaround.ui

import com.tide.barsaround.di.activity.ActivityComponent
import com.tide.barsaround.di.activity.ActivityComponentBuilder
import com.tide.barsaround.di.activity.ActivityModule
import com.tide.barsaround.di.activity.ActivityScope
import com.tide.barsaround.di.fragment.MainActivityFragmentBindingModule
import com.tide.barsaround.presenters.MainActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [
    MainActivityComponent.MainActivityModule::class,
    MainActivityFragmentBindingModule::class
])
interface MainActivityComponent : ActivityComponent<MainActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MainActivityModule, MainActivityComponent>

    @Module
    class MainActivityModule(activity: MainActivity) : ActivityModule<MainActivity>(activity) {

        @Provides
        fun providesMainActivityPresenter() = MainActivityPresenter()
    }
}
