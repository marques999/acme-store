package org.marques999.acme.store.views.order

import android.os.Bundle
import android.app.ProgressDialog
import android.support.v7.widget.GridLayoutManager

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs

import kotlinx.android.synthetic.main.fragment_history.*

import android.content.Intent
import android.content.Context

import org.marques999.acme.store.model.Order
import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.views.BottomNavigationAdapter
import org.marques999.acme.store.views.main.MainActivityFragment
import org.marques999.acme.store.views.main.MainActivityListener

class OrderHistoryFragment : MainActivityFragment(R.layout.fragment_history), OrderHistoryListener {

    /**
     */
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private val orders = ArrayList<Order>()
    private var mainActivityListener: MainActivityListener? = null

    /**
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivityListener = context as? MainActivityListener
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        mainActivityListener = null
    }

    /**
     */
    private fun onSuccess(adapter: OrderHistoryAdapter) = Consumer<List<Order>> {
        orders.clear()
        orders.addAll(it)
        adapter.refreshItems(it)
        progressDialog.dismiss()
        mainActivityListener?.onUpdateBadge(BottomNavigationAdapter.HISTORY, it.size)
    }

    /**
     */
    override fun onRefresh() {

        progressDialog.show()

        (activity.application as AcmeStore).api.getOrders().observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onSuccess(orderHistory_container.adapter as OrderHistoryAdapter), Consumer {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
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
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            clearOnScrollListeners()
            adapter = OrderHistoryAdapter(this@OrderHistoryFragment)
        }

        if (savedInstanceState == null) {
            onRefresh()
        } else {
            orders.clear()
            orders.addAll(savedInstanceState.getParcelableArrayList(BUNDLE_ORDER))
            mainActivityListener?.onUpdateBadge(BottomNavigationAdapter.HISTORY, orders.size)
            (orderHistory_container.adapter as OrderHistoryAdapter).refreshItems(orders)
        }
    }

    /**
     */
    fun registerOrder(order: Order) {
        orders.add(0, order)
        mainActivityListener?.onUpdateBadge(BottomNavigationAdapter.HISTORY, orders.size)
        (orderHistory_container.adapter as OrderHistoryAdapter).refreshItems(orders)
    }

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.bundles.ORDER"
    }
}