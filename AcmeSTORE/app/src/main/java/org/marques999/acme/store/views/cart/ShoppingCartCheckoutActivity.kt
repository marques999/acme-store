package org.marques999.acme.store.views.cart

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product.*
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.views.BackButtonActivity


class ShoppingCartCheckoutActivity : BackButtonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        /*intent.getParcelableExtra<OrderProduct>(ORDER_PRODUCT).let {

            it.let {

                productView_model.text = it.product.name
                productView_brand.text = it.product.brand
                productView_barcode.text = it.product.barcode
                productView_description.text = it.product.description
                productView_price.text = AcmeUtils.formatCurrency(it.product.price)

                Picasso.with(this).load(
                        it.product.image_uri
                ).fit().centerInside().into(productView_photo)
            }

        }*/

    }

}