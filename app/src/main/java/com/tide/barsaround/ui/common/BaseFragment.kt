package com.tide.barsaround.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            unpackArguments(it)
        }

        setupFragmentComponent()
        super.onCreate(savedInstanceState)
    }

    protected open fun unpackArguments(@Suppress("UNUSED_PARAMETER") bundle: Bundle) {}

    private fun setupFragmentComponent() {
        injectMembers(context as HasFragmentSubcomponentBuilders)
    }

    protected abstract fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders)
}
