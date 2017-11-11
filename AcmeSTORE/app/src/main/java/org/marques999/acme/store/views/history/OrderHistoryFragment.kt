package org.marques999.acme.store.views.history

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.model.Order
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.views.order.OrderViewActivity

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_history.*

class OrderHistoryFragment : Fragment(), OrderHistoryListener {

    /**
     */
    private lateinit var orders: ArrayList<Order>
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(BUNDLE_ORDER, orders)
    }

    /**
     */
    override fun onItemSelected(token: String) {

        (activity.application as AcmeStore).acmeApi.getOrder(token).subscribeOn(
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        R.layout.fragment_history, container, false
    )

    /**
     */
    private fun refreshOrders() {

        (history_recyclerView.adapter as OrderHistoryAdapter).let { adapter ->

            progressDialog.show()

            (activity.application as AcmeStore).acmeApi.getOrders().observeOn(
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.loginActivity_progress)

        history_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = OrderHistoryAdapter(this@OrderHistoryFragment)
        }

        if (savedInstanceState == null) {
            refreshOrders()
        } else {
            orders = savedInstanceState.getParcelableArrayList(BUNDLE_ORDER)
            (history_recyclerView.adapter as OrderHistoryAdapter).refreshItems(orders)
        }
    }

    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.bundles.ORDER"
    }
}