package com.tide.barsaround.di.view

interface ViewComponentBuilder<M : ViewModule<*>, C : ViewComponent<*>> {
    fun viewModule(viewModule: M): ViewComponentBuilder<M, C>

    fun build(): C
}
