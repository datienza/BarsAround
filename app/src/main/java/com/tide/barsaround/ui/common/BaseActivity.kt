package com.tide.barsaround.ui.common

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.tide.barsaround.BarsAroundApplication
import com.tide.barsaround.R
import com.tide.barsaround.di.activity.HasActivitySubcomponentBuilders

abstract class BaseActivity : AppCompatActivity() {
    protected var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        intent?.extras?.let {
            retrieveIntentBundle(it)
        }

        setupActivityComponent()
        super.onCreate(savedInstanceState)
    }

    protected open fun retrieveIntentBundle(@Suppress("UNUSED_PARAMETER") bundle: Bundle) {}

    private fun setupActivityComponent() {
        injectMembers(BarsAroundApplication.get(this))
    }

    protected abstract fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders)

    protected fun initFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            createFragment()
            launchFragment()
        } else if (fragment is BaseFragment) {
            currentFragment = fragment
        }
    }

    protected abstract fun createFragment()

    private fun launchFragment() {
        val currentFragment = currentFragment
        if (currentFragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.fragmentContainer, currentFragment).commit()
        } else {
            throw RuntimeException("no fragment set")
        }
    }

    protected open fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    protected fun setHomeAsUpEnabled(@DrawableRes arrowBackDrawable: Int) {
        supportActionBar?.let {
            it.setHomeAsUpIndicator(arrowBackDrawable)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }
}
