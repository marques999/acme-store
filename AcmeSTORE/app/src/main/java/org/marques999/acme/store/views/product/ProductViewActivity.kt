package org.marques999.acme.store.views.product

import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.views.ViewUtils

import kotlinx.android.synthetic.main.activity_product.*
import org.marques999.acme.store.model.Product

class ProductViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewInformation(intent.getParcelableExtra(ORDER_PRODUCT))
    }

    private fun viewProduct(product: Product) {

        productView_model.text = product.name
        productView_brand.text = product.brand
        productView_barcode.text = product.barcode
        productView_description.text = product.description
        productView_price.text = ViewUtils.formatCurrency(product.price)

        Picasso.with(this).load(
            product.image_uri
        ).fit().centerInside().into(productView_photo)
    }

    private fun viewInformation(orderProduct: OrderProduct) {

        viewProduct(orderProduct.product)
        productView_quantity.text = orderProduct.quantity.toString()

        intent.getBooleanExtra(ORDER_ACTIVE, false).let {
            productView_plus.isEnabled = it
            productView_minus.isEnabled = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val ORDER_ACTIVE = "org.marques999.acme.store.ORDER_ACTIVE"
        val ORDER_PRODUCT = "org.marques999.acme.store.ORDER_PRODUCT"
    }
}