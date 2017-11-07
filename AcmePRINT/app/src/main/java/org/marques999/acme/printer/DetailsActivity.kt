package org.marques999.acme.printer

import android.os.Bundle

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_details.*

import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.orders.Order
import org.marques999.acme.printer.views.RecyclerAdapter

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class DetailsActivity : AppCompatActivity() {

    /**
     */
    private val onRefresh = Consumer<Order> {
        detailsActivity_recyclerView.adapter = RecyclerAdapter(it)
    }

    /**
     */
    private fun fetchInformation() = intent.extras.getString(AcmePrinter.EXTRA_TOKEN)?.let {

        (application as AcmePrinter).acmeApi!!.getOrder(it).subscribeOn(
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

        if (outState != null && detailsActivity_recyclerView?.adapter is RecyclerAdapter) {

            outState.putSerializable(
                AcmePrinter.BUNDLE_ORDER,
                (detailsActivity_recyclerView.adapter as RecyclerAdapter).getState()
            )
        }
    }

    /**
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            detailsActivity_recyclerView.adapter = RecyclerAdapter(
                it.getSerializable(AcmePrinter.BUNDLE_ORDER) as Order
            )
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        detailsActivity_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        OverScrollDecoratorHelper.setUpOverScroll(
            detailsActivity_recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        if (savedInstanceState == null) {
            fetchInformation()
        }
    }
}