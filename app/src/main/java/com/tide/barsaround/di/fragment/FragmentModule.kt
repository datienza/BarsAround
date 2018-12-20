package com.tide.barsaround.di.fragment

import androidx.fragment.app.Fragment

import dagger.Module
import dagger.Provides

@Module
abstract class FragmentModule<T : Fragment>(protected val fragment: T) {

    @Provides
    @FragmentScope
    fun provideFragment(): T {
        return fragment
    }
}
