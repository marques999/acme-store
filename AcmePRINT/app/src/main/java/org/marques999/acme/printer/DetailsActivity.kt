package org.marques999.acme.printer

import android.os.Bundle

import org.marques999.acme.printer.views.RecyclerAdapter

import kotlinx.android.synthetic.main.activity_details.*

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class DetailsActivity : AppCompatActivity() {

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {

        super.onSaveInstanceState(outState)

        if (detailsActivity_recyclerView?.adapter is RecyclerAdapter) {

            outState?.putParcelable(
                BUNDLE_ORDER,
                (detailsActivity_recyclerView.adapter as RecyclerAdapter).order
            )
        }
    }

    /**
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            detailsActivity_recyclerView.adapter = RecyclerAdapter(
                it.getParcelable(BUNDLE_ORDER)
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

            detailsActivity_recyclerView.adapter = RecyclerAdapter(
                intent.extras.getParcelable(MainActivity.EXTRA_ORDER)
            )
        }
    }

    /**
     */
    companion object {
        private val BUNDLE_ORDER = "org.marques999.acme.printer.BUNDLE_ORDER"
    }
}