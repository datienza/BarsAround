package com.tide.barsaround.presenters

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.tide.barsaround.contracts.BarsFragmentContract
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.helpers.LocationPermissionImpl
import com.tide.barsaround.helpers.LocationTrackerImpl
import com.tide.barsaround.presenters.common.BasePresenterTest
import com.tide.barsaround.repository.BAR_TYPE
import com.tide.barsaround.repository.NearbyRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val LATITUDE = 32.23
private const val LONGITUDE = 15.14

class BarsFragmentPresenterTest : BasePresenterTest<BarsFragmentPresenter>() {

    private var view = mock<BarsFragmentContract.View>()

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
        presenter = BarsFragmentPresenter(
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
    fun loadList() {
        doAnswer {
            val locationCallback = it.arguments[0] as LocationCallback
            locationCallback.onLocationResult(locationResult)
        }.whenever(locationTrackerImpl).startLocationUpdates(any())
        whenever(nearbyRepository.getNearbyPlaces(eq(BAR_TYPE), any())).thenReturn(createFakeBarsSingleResponse())
        presenter.loadList()
        verify(locationTrackerImpl).startLocationUpdates(any())
        assertEquals(presenter.lastLocation?.latitude, LATITUDE)
        assertEquals(presenter.lastLocation?.longitude, LONGITUDE)
        verify(nearbyRepository).getNearbyPlaces(eq(BAR_TYPE), any())
        verify(view).hideList()
        verify(view).showProgressBar()
        verify(view).hideProgressBar()
        verify(view, times(2)).hideEmptyResultView()
        verify(view).populateList(any())
        verify(view).hideSwipeRefreshingView()
        verifyNoMoreInteractions(locationTrackerImpl, nearbyRepository, view)
    }

    @Test
    fun loadListWhenErrorGettingNearByPlaces() {
        doAnswer {
            val locationCallback = it.arguments[0] as LocationCallback
            locationCallback.onLocationResult(locationResult)
        }.whenever(locationTrackerImpl).startLocationUpdates(any())
        whenever(nearbyRepository.getNearbyPlaces(eq(BAR_TYPE), any())).thenReturn(Single.error(Throwable()))
        presenter.loadList()
        assertEquals(presenter.lastLocation?.latitude, LATITUDE)
        assertEquals(presenter.lastLocation?.longitude, LONGITUDE)
        verify(nearbyRepository).getNearbyPlaces(eq(BAR_TYPE), any())
        verify(locationTrackerImpl).startLocationUpdates(any())
        verify(view).hideEmptyResultView()
        verify(view, times(2)).hideList()
        verify(view).showProgressBar()
        verify(view).showEmptyResultView()
        verify(view).hideSwipeRefreshingView()
        verifyNoMoreInteractions(locationTrackerImpl, nearbyRepository, view)
    }

    @Test
    fun loadListWhenLocationResultNoCallback() {
        presenter.loadList()
        verify(locationTrackerImpl).startLocationUpdates(any())
        verifyNoMoreInteractions(locationTrackerImpl)
        verifyZeroInteractions(nearbyRepository, view)
    }

    @Test
    fun destroyAllDisposables() {
        presenter.destroyAllDisposables()
        verify(locationTrackerImpl).stopLocationUpdates(any())
        verifyNoMoreInteractions(locationTrackerImpl)
    }

    private fun createFakeBarsSingleResponse() = Single.just(listOf(Bar()))
}
