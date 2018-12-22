package com.tide.barsaround.ui

import android.annotation.SuppressLint
import android.content.Context
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
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
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
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        map?.isMyLocationEnabled = true
        presenter.init()
    }

    override fun displayData(bars: List<Result>) {
        map?.clear()
        bars.forEach {
            val lat = it.geometry.location.lat
            val lng = it.geometry.location.lng
            val barName = it.name
            val markerOptions = MarkerOptions()
            val latLng = LatLng(lat, lng)
            // Location of Marker on Map
            markerOptions.position(latLng)
            // Title for Marker
            markerOptions.title(barName)
            // Color or drawable for marker
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            // add marker
            map?.addMarker(markerOptions)
            // move map camera
            map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            map?.animateCamera(CameraUpdateFactory.zoomTo(13F))
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.destroyAllDisposables()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
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

    override fun locationPermissionGranted() {
        presenter.init()
    }
}
