package org.marques999.acme.store.views.order

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import kotlinx.android.synthetic.main.fragment_history.*

import org.marques999.acme.store.*
import org.marques999.acme.store.model.Order
import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.views.MainActivityFragment

import android.support.v7.widget.LinearLayoutManager

class OrderHistoryFragment : MainActivityFragment(R.layout.fragment_history), OrderHistoryListener {

    /**
     */
    private lateinit var orders: ArrayList<Order>
    private lateinit var progressDialog: ProgressDialog

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

        (activity.application as AcmeStore).api.getOrder(token).subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe(
            Consumer {
                startActivity(Intent(
                    activity, OrderViewActivity::class.java
                ).putExtra(
                    OrderViewActivity.EXTRA_ORDER, it
                ))
            },
            HttpErrorHandler(context)
        )
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_progressLoading)

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
            (orderHistory_container.adapter as OrderHistoryAdapter).refreshItems(orders)
        }
    }

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.bundles.ORDER"
    }
}