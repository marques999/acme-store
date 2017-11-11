package org.marques999.acme.store.views.order

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_order.*

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.views.ViewUtils

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class OrderViewActivity : AppCompatActivity() {
    /**
     */
    private lateinit var orderProductAdapter: OrderViewProductAdapter

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        viewOrder(
            intent.extras.getParcelable(OrderViewActivity.EXTRA_ORDER),
            savedInstanceState == null
        )
    }

    /**
     */
    private fun viewOrder(orderJSON: OrderJSON, initialize: Boolean) {

        if (initialize) {
            orderProductAdapter = OrderViewProductAdapter(orderJSON.products)
        }

        orderView_token.text = orderJSON.token
        orderView_count.text = orderJSON.count.toString()
        orderView_total.text = ViewUtils.formatCurrency(orderJSON.total)
        orderView_createdAt.text = ViewUtils.formatDateTime(orderJSON.created_at)
        orderView_modifiedAt.text = ViewUtils.formatDateTime(orderJSON.updated_at)

        orderView_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = orderProductAdapter
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