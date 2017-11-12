package org.marques999.acme.store.views.catalog

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.store.R

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.order.CatalogListener

class ProductCatalogFragment : MainActivityFragment(R.layout.fragment_catalog), CatalogListener, ProductCatalogListener {

    private lateinit var products: ArrayList<Product>
    private lateinit var progressDialog: ProgressDialog

    private var productCatalogListener: ProductCatalogListener? = null


    override fun onPurchase(product: String) {
        productCatalogListener?.onPurchase(product)
    }


    override fun onRefresh() {
        AcmeDialogs.buildOk(activity, R.string.actionBar_catalog).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(ProductCatalogFragment.BUNDLE_PRODUCTS, products)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
            R.layout.fragment_catalog, container, false
    )

    override fun onItemSelected(product: Product) {

        startActivity(Intent(
                activity, CatalogProductViewActivity::class.java
        ).putExtra(
                CatalogProductViewActivity.PRODUCT, product
        ))
    }

    private fun refreshProducts() {
        (catalog_recyclerView.adapter as ProductCatalogAdapter).let { adapter ->

            progressDialog.show()

            (activity.application as AcmeStore).api.getProducts().observeOn(
                    AndroidSchedulers.mainThread()
            ).subscribeOn(
                    Schedulers.io()
            ).subscribe({
                products = ArrayList(it)
                adapter.refreshItems(it)
            }, {

                HttpErrorHandler(context).accept(it)
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_progressLoading)

        catalog_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager  = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = ProductCatalogAdapter(this@ProductCatalogFragment, this@ProductCatalogFragment)
        }

        if (savedInstanceState == null) {
            refreshProducts()
        } else {
            products = savedInstanceState.getParcelableArrayList(BUNDLE_PRODUCTS)
            (catalog_recyclerView.adapter as ProductCatalogAdapter).refreshItems(products)
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        (activity as? ProductCatalogListener)?.let{
            productCatalogListener = it
        }
    }

    override fun onDetach() {
        super.onDetach()
        productCatalogListener = null
    }

    companion object {
        private val BUNDLE_PRODUCTS = "org.marques999.acme.bundles.PRODUCTS"
    }

}