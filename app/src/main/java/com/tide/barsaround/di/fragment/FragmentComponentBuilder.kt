package com.tide.barsaround.di.fragment

interface FragmentComponentBuilder<M : FragmentModule<*>, C : FragmentComponent<*>> {
    fun fragmentModule(activityModule: M): FragmentComponentBuilder<M, C>
    fun build(): C
}
