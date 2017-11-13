package org.marques999.acme.store.views.order

import android.os.Bundle
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.views.BackButtonActivity
import org.marques999.acme.store.views.product.ProductViewActivity

import kotlinx.android.synthetic.main.activity_order.*

import org.marques999.acme.store.model.QrCode
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.model.OrderProduct

class OrderViewActivity : BackButtonActivity(), OrderViewListener {

    /**
     */
    override fun onItemSelected(orderProduct: OrderProduct) = startActivity(Intent(
        this, ProductViewActivity::class.java
    ).putExtra(
        ProductViewActivity.EXTRA_PRODUCT, orderProduct.product
    ))

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

            val qrBitmap = AcmeUtils.encodeQrCode(this, String(
                it.token.toByteArray(), charset("ISO-8859-1")
            ))

            orderView_recyclerView.adapter = OrderViewAdapter(it, QrCode(qrBitmap), this)
        }
    }

    /**
     */
    companion object {
        internal val EXTRA_ORDER = "org.marques999.acme.extras.ORDER"
    }
}