package com.tide.barsaround.helpers

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class LocationTrackerImplTest {

    private lateinit var locationTrackerImpl: LocationTrackerImpl

    private var fusedLocationProviderClient = mock<FusedLocationProviderClient>()

    private var locationRequest = mock<LocationRequest>()

    private var locationPermissionImpl = mock<LocationPermissionImpl>()

    private var locationCallback = LocationCallback()

    @Before
    fun setUp() {
        locationTrackerImpl = LocationTrackerImpl(fusedLocationProviderClient, locationRequest, locationPermissionImpl)
    }

    @Test
    fun startLocationUpdatesWhenPermissionGranted() {
        whenever(locationPermissionImpl.checkLocationPermission()).thenReturn(true)
        locationTrackerImpl.startLocationUpdates(locationCallback)
        verify(locationPermissionImpl).checkLocationPermission()
        verify(fusedLocationProviderClient).requestLocationUpdates(any(), any(), eq(null))
        verifyNoMoreInteractions(locationPermissionImpl, fusedLocationProviderClient)
    }

    @Test
    fun startLocationUpdatesWhenPermissionNotGranted() {
        whenever(locationPermissionImpl.checkLocationPermission()).thenReturn(false)
        locationTrackerImpl.startLocationUpdates(locationCallback)
        verify(locationPermissionImpl).checkLocationPermission()
        verifyNoMoreInteractions(locationPermissionImpl)
        verifyZeroInteractions(fusedLocationProviderClient)
    }

    @Test
    fun stopLocationUpdates() {
        locationTrackerImpl.stopLocationUpdates(locationCallback)
        verify(fusedLocationProviderClient).removeLocationUpdates(locationCallback)
        verifyNoMoreInteractions(fusedLocationProviderClient)
    }
}
