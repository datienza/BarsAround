package com.tide.barsaround.ui.common

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    var data: List<D>? = null

    override fun getItemCount() = data?.size ?: 0
}
