package org.marques999.acme.store.catalog

import android.os.Bundle
import android.content.Context
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_dummy_list.*

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.views.MainActivityFragment

class ProductCatalogFragment : MainActivityFragment(R.layout.fragment_dummy_list), ProductCatalogListener {

    /**
     */
    override fun onPurchase(product: String) {
        productCatalogListener?.onPurchase(product)
    }

    /**
     */
    override fun onRefresh() {
        AcmeDialogs.buildOk(activity, R.string.actionBar_catalog).show()
    }

    /**
     */
    private var productCatalogListener: ProductCatalogListener? = null

    /**
     */
    override fun onAttach(context: Context?) {

        super.onAttach(context)

        (activity as? ProductCatalogListener)?.let {
            productCatalogListener = it
        }
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        productCatalogListener = null
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = ProductCatalogAdapter(ProductCatalogContent.products, this)
    }
}