package com.tide.barsaround.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tide.barsaround.R
import com.tide.barsaround.contracts.LocationPermissionContract
import com.tide.barsaround.contracts.MainActivityContract
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.di.activity.HasActivitySubcomponentBuilders
import com.tide.barsaround.di.fragment.FragmentComponentBuilder
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.extensions.addFragment
import com.tide.barsaround.extensions.disableShiftMode
import com.tide.barsaround.extensions.setSelectedViewTextStyle
import com.tide.barsaround.presenters.MainActivityPresenter
import com.tide.barsaround.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

private const val MY_PERMISSIONS_REQUEST_LOCATION = 9

class MainActivity : BaseActivity(), HasFragmentSubcomponentBuilders, MainActivityContract.View, OnBarSelectedListener {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        initBottomNavigation(R.id.navigationBars)
        presenter.checkLocationPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun createFragment() {
        // Nothing to do
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

    private fun initBottomNavigation(selectedItemId: Int) {
        presenter.navigationItemSelected(selectedItemId)
        bottomNavigationView.disableShiftMode()
        bottomNavigationView.selectedItemId = selectedItemId
        bottomNavigationView.setSelectedViewTextStyle(selectedItemId, Typeface.BOLD)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        presenter.navigationItemSelected(item.itemId)
    }

    override fun onBarSelected(result: Result) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun isAboveAndroidMarshmallow() = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    override fun checkSelfLocationPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

    override fun requestLocationPermission() =
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    checkSelfLocationPermission() == PackageManager.PERMISSION_GRANTED
                ) {
                    presenter.notifyLocationPermissionGranted()
                } else {
                    Toast.makeText(
                        this,
                        "Location Permission has been denied, can not search the places you want.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    override fun getNavigationItemMapId() = R.id.navigationMap

    override fun getNavigationItemBarId() = R.id.navigationBars

    override fun navigateToMapTab() {
        val mapFragment = MapFragment.newInstance()
        addFragment(mapFragment, R.id.fragmentContainer) }

    override fun navigateToBarTab() {
        val barsFragment = BarsFragment.newInstance()
        addFragment(barsFragment, R.id.fragmentContainer)
    }

    override fun notifyTabPermissionGranted() =
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as LocationPermissionContract).locationPermissionGranted()
}
