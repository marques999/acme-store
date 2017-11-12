package org.marques999.acme.store.views.product

import android.os.Bundle

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import com.squareup.picasso.Picasso

import org.marques999.acme.store.model.Product
import org.marques999.acme.store.views.BackButtonActivity

import kotlinx.android.synthetic.main.activity_product.*

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class ProductViewActivity : BackButtonActivity() {

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        OverScrollDecoratorHelper.setUpOverScroll(productView_scrollView)

        intent.getBooleanExtra(EXTRA_PURCHASED, false).let {
            productView_plus.isEnabled = it
            productView_minus.isEnabled = it
        }

        intent.getParcelableExtra<Product>(EXTRA_PRODUCT).let {

            productView_model.text = it.name
            productView_brand.text = it.brand
            productView_barcode.text = it.barcode
            productView_description.text = it.description
            productView_price.text = AcmeUtils.formatCurrency(it.price)

            Picasso.with(this).load(
                it.image_uri
            ).fit().centerInside().into(productView_photo)
        }
    }

    /**
     */
    companion object {
        val EXTRA_PURCHASED = "org.marques999.acme.store.extra.ORDER_PURCHASED"
        val EXTRA_PRODUCT = "org.marques999.acme.store.extra.ORDER_PRODUCT"
    }
}