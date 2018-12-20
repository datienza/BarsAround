package com.tide.barsaround.di.view

import dagger.Module
import dagger.Provides

@Module
abstract class ViewModule<T>(protected val view: T) {

    @Provides
    @ViewScope
    fun provideView(): T {
        return view
    }
}
