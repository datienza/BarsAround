package com.tide.barsaround.presenters

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.tide.barsaround.contracts.MapFragmentContract
import com.tide.barsaround.helpers.LocationPermission
import com.tide.barsaround.helpers.LocationPermissionImpl
import com.tide.barsaround.helpers.LocationTracker
import com.tide.barsaround.helpers.LocationTrackerImpl
import com.tide.barsaround.presenters.common.BasePresenter
import com.tide.barsaround.repository.BAR_TYPE
import com.tide.barsaround.repository.NearbyRepository
import io.reactivex.Scheduler

class MapFragmentPresenter(
    private val nearByRepository: NearbyRepository,
    private val uiScheduler: Scheduler,
    private val locationTrackerImpl: LocationTrackerImpl,
    private val locationPermissionImpl: LocationPermissionImpl
) : BasePresenter<MapFragmentContract.View>(),
    LocationTracker by locationTrackerImpl,
    LocationPermission by locationPermissionImpl {

    private var lastLocation: Location? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach { location ->
                lastLocation = location
                loadData()
            }
        }
    }

    fun loadMap() {
        startLocationUpdates(locationCallback)
    }

    fun loadData() {
        lastLocation?.let { location ->
            val locationAttribute = location.latitude.toString() + "," + location.longitude.toString()
            addDisposable(
                nearByRepository.getNearbyPlaces(BAR_TYPE, locationAttribute)
                    .observeOn(uiScheduler)
                    .subscribe({ barsResponse ->
                        barsResponse?.let { bars ->
                            bars.forEach { it.setDistanceFromLocation(lastLocation) }
                            mvpView?.displayData(location, bars)
                        }
                    }, {
                    })
            )
        }
    }

    override fun destroyAllDisposables() {
        super.destroyAllDisposables()
        stopLocationUpdates(locationCallback)
    }
}
