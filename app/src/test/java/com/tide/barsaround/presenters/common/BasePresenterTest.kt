package com.tide.barsaround.presenters.common

import io.reactivex.disposables.Disposable
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

abstract class BasePresenterTest<T : BasePresenter<*>> {

    protected lateinit var presenter: T

    @Test
    fun presenterIsNotNull() {
        assertNotNull(presenter)
    }

    @Test
    fun testViewIsAttached() {
        assertTrue(presenter.isViewAttached())
    }

    @Test
    fun subscriptionsReady() {
        assertFalse(presenter.isDisposableReady())
        presenter.addDisposable(object : Disposable {
            override fun dispose() {
            }

            override fun isDisposed(): Boolean {
                return false
            }
        })
        assertTrue(presenter.isDisposableReady())
        presenter.destroyAllDisposables()
    }
}
