package com.tide.barsaround.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tide.barsaround.ui.adapters.BarsAdapter
import com.tide.barsaround.R
import com.tide.barsaround.contracts.BarsFragmentContract
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.presenters.BarsFragmentPresenter
import com.tide.barsaround.ui.common.BaseListFragment
import com.tide.barsaround.ui.common.SpacingItemDecoration
import javax.inject.Inject

class BarsFragment : BaseListFragment<BarsAdapter, Result, BarsAdapter.BarViewHolder>(), BarsFragmentContract.View {

    @Inject
    lateinit var presenter: BarsFragmentPresenter

    companion object {
        fun newInstance() = BarsFragment()
    }

    private var onBarSelectedListener: OnBarSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_base_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.init()
    }

    override fun onPause() {
        super.onPause()
        presenter.destroyAllDisposables()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders) {
        (hasFragmentSubcomponentBuilders
                .getFragmentComponentBuilder(BarsFragment::class.java) as BarsFragmentComponent.Builder)
                .fragmentModule(BarsFragmentComponent.BarsFragmentModule(this))
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

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return context?.let {
            val verticalSpaceItemDecoration = SpacingItemDecoration(it)
            verticalSpaceItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
            verticalSpaceItemDecoration
        }
    }

    override fun setUpList() {
        super.setUpList()
        adapter.onBarSelectedListener = onBarSelectedListener
    }

    override fun locationPermissionGranted() {
        presenter.init()
    }
}
