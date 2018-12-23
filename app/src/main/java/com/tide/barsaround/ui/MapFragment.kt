package com.tide.barsaround.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tide.barsaround.R
import com.tide.barsaround.contracts.MapFragmentContract
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.helpers.MY_PERMISSIONS_REQUEST_LOCATION
import com.tide.barsaround.presenters.MapFragmentPresenter
import com.tide.barsaround.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

class MapFragment : BaseFragment(), MapFragmentContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: MapFragmentPresenter

    private var map: GoogleMap? = null

    companion object {
        fun newInstance() = MapFragment()
    }

    private var onBarSelectedListener: OnBarSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_map, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.let { it ->
            it.mapType = GoogleMap.MAP_TYPE_NORMAL
            it.isMyLocationEnabled = true
            it.setOnMyLocationButtonClickListener {
                presenter.loadMap()
                true
            }
            presenter.loadMap()
        }
    }

    override fun displayData(currentLocation: Location, bars: List<Bar>) {
        map?.let {
            it.clear()
            bars.forEach { bar ->
                val lat = bar.lat
                val lng = bar.lng
                val latLng = LatLng(lat, lng)
                val markerOptions = MarkerOptions().apply {
                    position(latLng)
                    title(bar.name)
                    snippet(bar.distance)
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                }
                it.addMarker(markerOptions)
            }
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(currentLocation.latitude, currentLocation.longitude), 15F)
            it.animateCamera(cameraUpdate)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.destroyAllDisposables()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders) {
        (hasFragmentSubcomponentBuilders
                .getFragmentComponentBuilder(MapFragment::class.java) as MapFragmentComponent.Builder)
                .fragmentModule(MapFragmentComponent.MapsFragmentModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBarSelectedListener) {
            onBarSelectedListener = context
        } else {
            throw IllegalStateException(activity!!::class
                    .simpleName + " must implement " + OnBarSelectedListener::class.java
                    .simpleName)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun requestLocationPermission() =
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )

    override fun onLocationPermissionGranted() {
        presenter.loadMap()
    }
}
