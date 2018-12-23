package com.tide.barsaround.presenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.tide.barsaround.contracts.MainActivityContract
import com.tide.barsaround.presenters.common.BasePresenterTest
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val NAVIGATION_ITEM_BARS_ID = 1
private const val NAVIGATION_ITEM_MAP_ID = 2

class MainActivityPresenterTest : BasePresenterTest<MainActivityPresenter>() {

    private var view = mock<MainActivityContract.View>()

    @Before
    fun setUp() {
        presenter = MainActivityPresenter()
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun navigationItemSelectedWhenViewNotAttached() {
        presenter.detachView()
        val isSelected = presenter.navigationItemSelected(NAVIGATION_ITEM_BARS_ID)
        assertFalse(isSelected)
        verifyZeroInteractions(view)
    }

    @Test
    fun navigationItemSelectedWhenSameItemIsSelected() {
        presenter.lastSelectedItemId = NAVIGATION_ITEM_BARS_ID
        val isSelected = presenter.navigationItemSelected(NAVIGATION_ITEM_BARS_ID)
        assertTrue(isSelected)
        verifyZeroInteractions(view)
    }

    @Test
    fun navigationItemSelectedWhenNavigatingToBarsTab() {
        presenter.lastSelectedItemId = NAVIGATION_ITEM_MAP_ID
        val isSelected = presenter.navigationItemSelected(NAVIGATION_ITEM_BARS_ID)
        assertTrue(isSelected)
        verify(view).navigateToBarTab()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun navigationItemSelectedWhenNavigatingToMapTab() {
        presenter.lastSelectedItemId = NAVIGATION_ITEM_BARS_ID
        val isSelected = presenter.navigationItemSelected(NAVIGATION_ITEM_MAP_ID)
        assertTrue(isSelected)
        verify(view).navigateToMapTab()
        verifyNoMoreInteractions(view)
    }
}
