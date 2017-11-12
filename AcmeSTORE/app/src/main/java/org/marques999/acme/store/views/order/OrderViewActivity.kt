package org.marques999.acme.store.views.order

import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_order.*

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderJSON

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class OrderViewActivity : AppCompatActivity() {

    /**
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {

        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        viewOrder(intent.getParcelableExtra(OrderViewActivity.EXTRA_ORDER))
    }

    /**
     */
    private fun viewOrder(orderJSON: OrderJSON) {

        orderView_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = OrderViewAdapter(orderJSON)
        }

        OverScrollDecoratorHelper.setUpOverScroll(
            orderView_recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )
    }

    /**
     */
    companion object {
        internal val EXTRA_ORDER = "org.marques999.acme.extras.ORDER"
    }
}