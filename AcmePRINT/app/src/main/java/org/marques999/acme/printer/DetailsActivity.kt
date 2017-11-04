package org.marques999.acme.printer

import android.os.Bundle
import android.app.Activity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_details.*

import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.orders.Order
import org.marques999.acme.printer.views.RecyclerAdapter

import android.support.v7.widget.LinearLayoutManager

class DetailsActivity : Activity() {

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.printer.ORDER"
    }

    /**
     */
    private val onRefresh = Consumer<Order> {
        details_rv.adapter = RecyclerAdapter(it)
    }

    /**
     */
    private fun fetchInformation() = intent.extras.getString(AcmePrinter.EXTRA_TOKEN)?.let {

        (application as AcmePrinter).acmeApi.getOrder(
            it
        ).subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe(
            onRefresh, HttpErrorHandler(this)
        )
    }

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {

        super.onSaveInstanceState(outState)

        if (outState != null && details_rv?.adapter is RecyclerAdapter) {
            outState.putSerializable(BUNDLE_ORDER, (details_rv.adapter as RecyclerAdapter).stateRestore)
        }
    }

    /**
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            details_rv.adapter = RecyclerAdapter(it.getSerializable(BUNDLE_ORDER) as Order)
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        details_rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        if (savedInstanceState == null) {
            fetchInformation()
        }
    }
}