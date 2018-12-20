package com.tide.barsaround.presenters

import com.tide.barsaround.contracts.BarsFragmentContract
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.presenters.common.BaseListPresenter
import com.tide.barsaround.repository.BAR_TYPE
import com.tide.barsaround.repository.NearbyRepository
import com.tide.barsaround.repository.RADIUS
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer

class BarsFragmentPresenter(
    private val nearByRepository: NearbyRepository,
    private val uiScheduler: Scheduler
) : BaseListPresenter<Result, BarsFragmentContract.View>() {
    override fun loadData(forceReload: Boolean) {
        val location = ""
        addDisposable(nearByRepository.getNearbyPlaces(BAR_TYPE, location, RADIUS)
            .observeOn(uiScheduler)
            .subscribe( Consumer {
                it.results?.let { bars ->
                    displayData(bars)
                }
            }))
    }
}
