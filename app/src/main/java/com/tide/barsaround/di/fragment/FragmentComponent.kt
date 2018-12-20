package com.tide.barsaround.di.fragment

import androidx.fragment.app.Fragment

import dagger.MembersInjector

interface FragmentComponent<A : Fragment> : MembersInjector<A>
