package com.tide.barsaround.di.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class UIActivityModule {

    @Provides
    @ActivityScope
    internal fun providesUIResolver(activity: AppCompatActivity) = UIResolver(activity)

    @Provides
    @ActivityScope
    internal fun providesUIResolution(uiResolver: UIResolver) = UIResolution(uiResolver)
}
