package org.marques999.acme.store.views.order

import android.os.Bundle
import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs

import kotlinx.android.synthetic.main.fragment_history.*

import org.marques999.acme.store.model.Order
import org.marques999.acme.store.api.HttpErrorHandler

import android.content.Intent
import android.content.Context

import org.marques999.acme.store.views.main.MainActivityFragment
import org.marques999.acme.store.views.main.MainActivityBadgeListener

class OrderHistoryFragment : MainActivityFragment(R.layout.fragment_history), OrderHistoryListener {

    /**
     */
    private lateinit var orders: ArrayList<Order>
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private var badgeListener: MainActivityBadgeListener? = null

    /**
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        badgeListener = context as? MainActivityBadgeListener
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        badgeListener = null
    }

    /**
     */
    override fun onRefresh() {

        (orderHistory_container.adapter as OrderHistoryAdapter).let { adapter ->

            progressDialog.show()

            (activity.application as AcmeStore).api.getOrders().observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeOn(
                Schedulers.io()
            ).subscribe({
                orders = ArrayList(it)
                adapter.refreshItems(it)
                progressDialog.dismiss()
                badgeListener?.onUpdateBadge(2, it.size)
            }, {
                progressDialog.dismiss()
                HttpErrorHandler(context).accept(it)
            })
        }
    }

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(BUNDLE_ORDER, orders)
    }

    /**
     */
    override fun onItemSelected(token: String) {

        progressDialog.show()

        (activity.application as AcmeStore).api.getOrder(token).subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe({
            progressDialog.dismiss()
            startActivity(Intent(
                activity, OrderViewActivity::class.java
            ).putExtra(
                OrderViewActivity.EXTRA_ORDER, it
            ))
        }, {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_loading)
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        orderHistory_container.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = OrderHistoryAdapter(this@OrderHistoryFragment)
        }

        if (savedInstanceState == null) {
            onRefresh()
        } else {
            orders = savedInstanceState.getParcelableArrayList(BUNDLE_ORDER)
            badgeListener?.onUpdateBadge(2, orders.size)
            (orderHistory_container.adapter as OrderHistoryAdapter).refreshItems(orders)
        }
    }

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.bundles.ORDER"
    }
}