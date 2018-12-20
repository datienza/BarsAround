package com.test.spacex.ui.adapter.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tide.barsaround.R
import com.tide.barsaround.data.model.Result
import com.tide.barsaround.ui.OnBarSelectedListener
import com.tide.barsaround.ui.common.BaseAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bar.*

class BarsAdapter(context: Context) : BaseAdapter<Result, BarsAdapter.BarViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    var onBarSelectedListener: OnBarSelectedListener? = null

    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {
        data?.get(position)?.let { result ->
            holder.bind(result)
            holder.itemView.setOnClickListener {
                onBarSelectedListener?.onBarSelected(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        val view = layoutInflater.inflate(R.layout.item_bar, parent, false)
        return BarsAdapter.BarViewHolder(view)
    }

    class BarViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(result: Result) {
            val context = containerView.context
            barNameText.text = result.name
        }
    }
}
