package com.tide.barsaround.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tide.barsaround.R
import com.tide.barsaround.contracts.BarsFragmentContract
import com.tide.barsaround.data.model.Bar
import com.tide.barsaround.di.fragment.HasFragmentSubcomponentBuilders
import com.tide.barsaround.helpers.MY_PERMISSIONS_REQUEST_LOCATION
import com.tide.barsaround.presenters.BarsFragmentPresenter
import com.tide.barsaround.ui.adapters.BarsAdapter
import com.tide.barsaround.ui.common.BaseListFragment
import kotlinx.android.synthetic.main.fragment_base_list.*
import javax.inject.Inject

class BarsFragment : BaseListFragment<BarsAdapter, Bar, BarsAdapter.BarViewHolder>(), BarsFragmentContract.View {

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
        presenter.loadList()
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
            throw IllegalStateException(
                activity!!::class
                    .simpleName + " must implement " + OnBarSelectedListener::class.java
                    .simpleName
            )
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationPermissionGranted() {
        presenter.loadList()
    }

    override fun requestLocationPermission() =
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )

    override fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return SwipeRefreshLayout.OnRefreshListener {
            baseListSwipeRefreshLayout.isRefreshing = true
            presenter.loadList()
        }
    }
}
