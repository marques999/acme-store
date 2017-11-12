package org.marques999.acme.store.views.catalog

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product.*
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.R
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.views.BackButtonActivity

class CatalogProductViewActivity : BackButtonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)

        intent.getParcelableExtra<Product>(PRODUCT).let {

            it.let {

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

    }

    companion object {
        val PRODUCT = "org.marques999.acme.store.PRODUCT"
    }
}