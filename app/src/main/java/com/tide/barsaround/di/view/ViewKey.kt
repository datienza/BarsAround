package com.tide.barsaround.di.view

import android.view.View

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class ViewKey(val value: KClass<out View>)
