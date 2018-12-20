package com.tide.barsaround.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.tide.barsaround.R
import com.tide.barsaround.di.activity.HasActivitySubcomponentBuilders
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.extensions.disableShiftMode
import com.tide.barsaround.extensions.setSelectedViewTextStyle
import com.tide.barsaround.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BaseActivity(), HasFragmentSubcomponentBuilders {

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        initFragment()
    }

    private fun initBottomNavigation(selectedItemId: Int) {
        bottomNavigationView.disableShiftMode()
        bottomNavigationView.selectedItemId = 0
        bottomNavigationView.setSelectedViewTextStyle(selectedItemId, Typeface.BOLD)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            presenter.navigateToTab(bottomNavigationView.selectedItemId, item.itemId)
            false
        }
    }

    override fun createFragment() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
            .getActivityComponentBuilder(MainActivity::class.java) as MainActivityComponent.Builder)
            .activityModule(MainActivityComponent.MainActivityModule(this))
            .build()
            .injectMembers(this)
    }

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *> =
        fragmentComponentBuilders[fragmentClass]!!.get()
}
