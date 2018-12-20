package com.tide.barsaround.di.view

import android.view.View

interface HasViewSubcomponentBuilders {
    fun getViewComponentBuilder(viewClass: Class<out View>): ViewComponentBuilder<*, *>
}
