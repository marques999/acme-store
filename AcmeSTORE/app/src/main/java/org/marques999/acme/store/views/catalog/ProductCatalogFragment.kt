package org.marques999.acme.store.views.catalog

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.content.Intent

import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.store.R

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.order.CatalogListener
import org.marques999.acme.store.views.product.ProductViewActivity

class ProductCatalogFragment : MainActivityFragment(
    R.layout.fragment_catalog
), CatalogListener, ProductCatalogListener {

    /**
     */
    private lateinit var products: ArrayList<Product>
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private var productCatalogListener: ProductCatalogListener? = null

    /**
     */
    override fun onPurchase(product: String) {
        productCatalogListener?.onPurchase(product)
    }

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(ProductCatalogFragment.BUNDLE_PRODUCTS, products)
    }

    /**
      */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_progressLoading)
    }

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        R.layout.fragment_catalog, container, false
    )

    /**
     */
    override fun onItemSelected(product: Product) {

        startActivity(Intent(
            activity, ProductViewActivity::class.java
        ).putExtra(
            ProductViewActivity.EXTRA_PRODUCT, product
        ).putExtra(
            ProductViewActivity.EXTRA_PURCHASED, false
        ))
    }

    /**
     */
    override fun onRefresh() {

        (catalog_recyclerView.adapter as ProductCatalogAdapter).let { adapter ->

            progressDialog.show()

            (activity.application as AcmeStore).api.getProducts().observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeOn(
                Schedulers.io()
            ).subscribe(Consumer {
                products = ArrayList(it)
                adapter.refreshItems(it)
            }, HttpErrorHandler(context))
        }
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        catalog_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = ProductCatalogAdapter(this@ProductCatalogFragment)
        }

        if (savedInstanceState == null) {
            onRefresh()
        } else {
            products = savedInstanceState.getParcelableArrayList(BUNDLE_PRODUCTS)
            (catalog_recyclerView.adapter as ProductCatalogAdapter).refreshItems(products)
        }
    }

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
    companion object {
        private val BUNDLE_PRODUCTS = "org.marques999.acme.bundles.PRODUCTS"
    }
}