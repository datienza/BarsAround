package com.tide.barsaround.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tide.barsaround.R
import kotlinx.android.synthetic.main.fragment_base_list.*
import javax.inject.Inject

abstract class BaseListFragment<A : BaseAdapter<J, H>, J, H : RecyclerView.ViewHolder> : BaseFragment() {

    @Inject
    protected lateinit var adapter: A

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
    }

    @LayoutRes
    protected open fun getLayoutRes(): Int {
        return R.layout.fragment_base_list
    }

    @CallSuper
    protected open fun setUpList() {
        baseListRecyclerView.layoutManager = getLayoutManager()
        baseListRecyclerView.adapter = adapter
        getItemDecoration()?.let {
            baseListRecyclerView.addItemDecoration(it)
        }
        baseListRecyclerView.setHasFixedSize(true)
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    protected open fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return context?.let {
            val verticalSpaceItemDecoration = SpacingItemDecoration(it)
            verticalSpaceItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
            verticalSpaceItemDecoration
        }
    }

    fun showProgressBar() {
        if (baseListProgressBar != null) {
            baseListProgressBar.alpha = UiConstants.ALPHA_OPAQUE
            baseListProgressBar.visibility = View.VISIBLE
        }
    }

    fun hideProgressBar() {
        if (baseListProgressBar != null && baseListProgressBar.visibility == View.VISIBLE) {
            AnimationHelper.animateView(baseListProgressBar, View.GONE, UiConstants.ALPHA_TRANSPARENT, resources.getInteger(android.R.integer.config_shortAnimTime))
        }
    }

    fun populateList(data: List<J>) {
        if (baseListProgressBar != null && baseListRecyclerView.visibility != View.VISIBLE) {
            // The animation will be executed when the recyclerview is inflated, because it doesn't seem to visible randomly
            baseListRecyclerView.post {
                if (isAdded) {
                    AnimationHelper.crossFadeAnimation(baseListProgressBar, baseListRecyclerView, resources.getInteger(android.R.integer.config_shortAnimTime))
                }
            }
        }

        adapter.data = data
        adapter.notifyDataSetChanged()
    }

    fun hideList() {
        baseListRecyclerView.visibility = View.GONE
    }
}
