package org.marques999.acme.printer

import android.os.Bundle
import android.app.Activity
import android.support.v7.widget.LinearLayoutManager

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_details.*

import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.orders.Order
import org.marques999.acme.printer.views.RecyclerAdapter

class DetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (details_rv.adapter != null) {
            return
        }

        details_rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        intent.extras.getString(MainActivity.EXTRA_TOKEN)?.let {

            (application as AcmePrinter).acmeApi.getOrder(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer<Order> {
                        details_rv.adapter = RecyclerAdapter(it)
                    }, HttpErrorHandler(this@DetailsActivity)
                )
        }
    }
}