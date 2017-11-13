package org.marques999.acme.store.views.product

import android.os.Bundle
import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.content.Context
import android.content.Intent

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.AcmeStore

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.views.main.MainActivityFragment
import org.marques999.acme.store.views.main.MainActivityBadgeListener
import org.marques999.acme.store.views.main.MainActivityCatalogListener

import kotlinx.android.synthetic.main.fragment_catalog.*

import org.marques999.acme.store.model.Product
import org.marques999.acme.store.api.HttpErrorHandler

class ProductCatalogFragment : MainActivityFragment(R.layout.fragment_catalog), ProductCatalogListener {

    /**
     */
    private lateinit var products: ArrayList<Product>
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private var badgeListener: MainActivityBadgeListener? = null
    private var catalogListener: MainActivityCatalogListener? = null

    /**
     */
    override fun onItemPurchased(product: Product) {
        catalogListener?.onPurchase(product)
    }

    /**
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(BUNDLE_PRODUCTS, products)
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_loading)
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
    override fun onItemSelected(product: Product) = startActivity(Intent(
        activity, ProductViewActivity::class.java
    ).putExtra(
        ProductViewActivity.EXTRA_PRODUCT, product
    ))

    /**
     */
    private fun initializeRecycler(products: List<Product>) = catalog_recyclerView.apply {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        clearOnScrollListeners()
        adapter = ProductCatalogAdapter(products, this@ProductCatalogFragment)
    }

    /**
     */
    override fun onRefresh() {

        progressDialog.show()

        (activity.application as AcmeStore).api.getProducts().observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe({
            progressDialog.dismiss()
            products = ArrayList(it)
            initializeRecycler(it)
            badgeListener?.onUpdateBadge(1, it.size)
        }, {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            onRefresh()
        } else {
            products = savedInstanceState.getParcelableArrayList(BUNDLE_PRODUCTS)
            badgeListener?.onUpdateBadge(1, products.size)
            initializeRecycler(products)
        }
    }

    /**
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        badgeListener = context as? MainActivityBadgeListener
        catalogListener = activity as? MainActivityCatalogListener
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        badgeListener = null
        catalogListener = null
    }

    /**
     */
    companion object {
        private val BUNDLE_PRODUCTS = "org.marques999.acme.bundles.PRODUCTS"
    }
}