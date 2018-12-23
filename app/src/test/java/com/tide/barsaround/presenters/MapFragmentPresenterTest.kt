package com.tide.barsaround.presenters

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.tide.barsaround.contracts.MapFragmentContract
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.helpers.LocationPermissionImpl
import com.tide.barsaround.helpers.LocationTrackerImpl
import com.tide.barsaround.presenters.common.BasePresenterTest
import com.tide.barsaround.repository.BAR_TYPE
import com.tide.barsaround.repository.NearbyRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val LATITUDE = 32.23
private const val LONGITUDE = 15.14

class MapFragmentPresenterTest : BasePresenterTest<MapFragmentPresenter>() {

    private var view = mock<MapFragmentContract.View>()

    private var nearbyRepository = mock<NearbyRepository>()

    private var locationTrackerImpl = mock<LocationTrackerImpl>()

    private var locationPermissionImpl = mock<LocationPermissionImpl>()

    private var location = mock<Location> {
        on { latitude } doReturn LATITUDE
        on { longitude } doReturn LONGITUDE
    }

    private var locationResult = mock<LocationResult> {
        on { locations } doReturn listOf(location)
    }

    @Before
    fun setUp() {
        presenter = MapFragmentPresenter(
            nearbyRepository,
            Schedulers.trampoline(),
            locationTrackerImpl,
            locationPermissionImpl
        )
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun loadMap() {
        doAnswer {
            val locationCallback = it.arguments[0] as LocationCallback
            locationCallback.onLocationResult(locationResult)
        }.whenever(locationTrackerImpl).startLocationUpdates(any())
        whenever(nearbyRepository.getNearbyPlaces(eq(BAR_TYPE), any())).thenReturn(createFakeBarsSingleResponse())
        presenter.loadMap()
        verify(locationTrackerImpl).startLocationUpdates(any())
        assertEquals(presenter.lastLocation?.latitude, LATITUDE)
        assertEquals(presenter.lastLocation?.longitude, LONGITUDE)
        verify(nearbyRepository).getNearbyPlaces(eq(BAR_TYPE), any())
        verify(view).displayData(any(), any())
        verifyNoMoreInteractions(locationTrackerImpl, nearbyRepository, view)
    }

    @Test
    fun loadListWhenLocationResultNoCallback() {
        presenter.loadMap()
        verify(locationTrackerImpl).startLocationUpdates(any())
        verifyNoMoreInteractions(locationTrackerImpl)
        verifyZeroInteractions(nearbyRepository, view)
    }

    private fun createFakeBarsSingleResponse() = Single.just(listOf(Bar()))
}
