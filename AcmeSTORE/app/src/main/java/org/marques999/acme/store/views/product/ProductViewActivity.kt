package org.marques999.acme.store.views.product

import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.views.ViewUtils

import kotlinx.android.synthetic.main.activity_product.*

class ProductViewActivity : AppCompatActivity() {

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getBooleanExtra(ORDER_ACTIVE, false).let {
            productView_plus.isEnabled = it
            productView_minus.isEnabled = it
        }

        intent.getParcelableExtra<OrderProduct>(ORDER_PRODUCT).let {

            it.product.let {

                productView_model.text = it.name
                productView_brand.text = it.brand
                productView_barcode.text = it.barcode
                productView_description.text = it.description
                productView_price.text = ViewUtils.formatCurrency(it.price)

                Picasso.with(this).load(
                    it.image_uri
                ).fit().centerInside().into(productView_photo)
            }

            productView_quantity.text = it.quantity.toString()
        }
    }

    /**
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    /**
     */
    companion object {
        val ORDER_ACTIVE = "org.marques999.acme.store.ORDER_ACTIVE"
        val ORDER_PRODUCT = "org.marques999.acme.store.ORDER_PRODUCT"
    }
}