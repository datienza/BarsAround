package com.tide.barsaround.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tide.barsaround.R
import com.tide.barsaround.contracts.MainActivityContract
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.di.activity.HasActivitySubcomponentBuilders
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.extensions.addFragment
import com.tide.barsaround.extensions.disableShiftMode
import com.tide.barsaround.presenters.MainActivityPresenter
import com.tide.barsaround.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"

class MainActivity : BaseActivity(), HasFragmentSubcomponentBuilders, MainActivityContract.View, OnBarSelectedListener {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        initBottomNavigation()
        initFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun createFragment() {
        currentFragment = BarsFragment.newInstance()
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

    private fun initBottomNavigation() {
        bottomNavigationView.disableShiftMode()
        bottomNavigationView.setOnNavigationItemSelectedListener {
            presenter.navigationItemSelected(it.itemId)
        }
    }

    override fun onBarSelected(bar: Bar) {
        val gmmIntentUri = Uri.parse(getString(R.string.geo_location_query, bar.lat, bar.lng) + Uri.encode(bar.name))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(GOOGLE_MAPS_PACKAGE)
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    override fun getNavigationItemMapId() = R.id.navigationMap

    override fun getNavigationItemBarId() = R.id.navigationBars

    override fun navigateToMapTab() {
        val mapFragment = MapFragment.newInstance()
        addFragment(mapFragment, R.id.fragmentContainer)
        currentFragment = mapFragment
    }

    override fun navigateToBarTab() {
        val barsFragment = BarsFragment.newInstance()
        addFragment(barsFragment, R.id.fragmentContainer)
        currentFragment = barsFragment
    }
}
