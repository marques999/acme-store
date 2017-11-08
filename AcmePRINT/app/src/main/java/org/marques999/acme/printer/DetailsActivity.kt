package org.marques999.acme.printer

import android.os.Bundle
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_details.*

import org.marques999.acme.printer.orders.Order
import org.marques999.acme.printer.views.RecyclerAdapter

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class DetailsActivity : AppCompatActivity() {

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {

        super.onSaveInstanceState(outState)

        if (detailsActivity_recyclerView?.adapter is RecyclerAdapter) {

            outState?.putSerializable(
                BUNDLE_ORDER,
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
                it.getSerializable(BUNDLE_ORDER) as Order
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

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            OverScrollDecoratorHelper.setUpOverScroll(
                detailsActivity_recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
        } else {
            OverScrollDecoratorHelper.setUpOverScroll(
                detailsActivity_recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
            )
        }

        if (savedInstanceState == null) {

            detailsActivity_recyclerView.adapter = RecyclerAdapter(
                intent.extras.getSerializable(MainActivity.EXTRA_ORDER) as Order
            )
        }
    }

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.printer.BUNDLE_ORDER"
    }
}