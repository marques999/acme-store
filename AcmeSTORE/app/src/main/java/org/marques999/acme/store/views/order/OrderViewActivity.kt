package org.marques999.acme.store.views.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_order.*

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.views.common.BackButtonActivity

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class OrderViewActivity : BackButtonActivity() {

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        orderView_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        intent.getParcelableExtra<OrderJSON>(OrderViewActivity.EXTRA_ORDER).let {
            orderView_recyclerView.adapter = OrderViewAdapter(it)
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