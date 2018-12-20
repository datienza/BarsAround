package com.tide.barsaround.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.test.spacex.ui.adapter.products.BarsAdapter
import com.tide.barsaround.R
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.ui.common.BaseListFragment

class BarsFragment : BaseListFragment<BarsAdapter, Result, BarsAdapter.BarViewHolder>() {

    companion object {
        fun newInstance() = BarsFragment()
    }

    private var onBarSelectedListener: OnBarSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_base_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.grey_line, null)?.let {
            itemDecoration.setDrawable(it)
        }
        return itemDecoration
    }

    override fun setUpList() {
        super.setUpList()
        adapter.onBarSelectedListener = onBarSelectedListener
    }
}