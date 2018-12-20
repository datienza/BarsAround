package com.tide.barsaround.di.view

import android.view.View

import dagger.MembersInjector

interface ViewComponent<A : View> : MembersInjector<A>
