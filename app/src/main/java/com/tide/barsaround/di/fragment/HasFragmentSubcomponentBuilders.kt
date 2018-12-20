package com.tide.barsaround.di.fragment

import androidx.fragment.app.Fragment

interface HasFragmentSubcomponentBuilders {
    fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *>
}
