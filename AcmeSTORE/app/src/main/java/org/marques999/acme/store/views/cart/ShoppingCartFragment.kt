package org.marques999.acme.store.views.cart

import android.view.View

import org.marques999.acme.store.model.Product
import org.marques999.acme.store.model.OrderProduct

import android.content.Intent
import android.content.DialogInterface
import android.content.ActivityNotFoundException

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.net.Uri
import android.os.Bundle
import android.app.Activity
import android.app.ProgressDialog

import kotlinx.android.synthetic.main.fragment_cart.*

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs

import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.product.ProductViewActivity

class ShoppingCartFragment : MainActivityFragment(R.layout.fragment_cart), ShoppingCartListener {

    /**
     */
    override fun onRefresh() {
        AcmeDialogs.buildOk(activity, R.string.actionBar_cart).show()
    }

    /**
     */
    private val shoppingCart = HashMap<String, OrderProduct>()

    /**
     */
    private lateinit var adapter: ShoppingCartAdapter
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    override fun onItemUpdated(barcode: String, delta: Int) {

        shoppingCart[barcode]?.apply {

            if (delta != 0 && (delta > 0 && quantity < 99) || (delta < 0 && quantity > 0)) {
                quantity += delta
                adapter.update(this)
            }
        }
    }

    /**
     */
    override fun onItemDeleted(barcode: String) {
        shoppingCart.remove(barcode)?.let { adapter.remove(it) }
        shoppingCart_checkout.isEnabled = shoppingCart.isNotEmpty()
    }

    /**
     */
    private fun registerPurchase(product: Product) {

        val orderProduct = OrderProduct(1, product)

        shoppingCart[product.barcode]?.let {
            orderProduct.quantity += it.quantity
            adapter.remove(it)
        }

        adapter.insert(orderProduct)
        shoppingCart.put(orderProduct.product.barcode, orderProduct)
        shoppingCart_checkout.isEnabled = shoppingCart.isNotEmpty()
    }

    /**
     */
    private val onFetchProduct = Consumer<Product> {
        registerPurchase(it)
        progressDialog.dismiss()
    }

    /**
     */
    override fun onItemSelected(barcode: String) {

        shoppingCart[barcode]?.let {

            startActivity(Intent(
                activity, ProductViewActivity::class.java
            ).putExtra(
                ProductViewActivity.EXTRA_PRODUCT, it
            ).putExtra(
                ProductViewActivity.EXTRA_ACTIVE, true
            ))
        }
    }

    /**
     */
    private fun fetchProduct(barcode: String) {

        progressDialog.show()

        (activity.application as AcmeStore).api.getProduct(barcode).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onFetchProduct,
            Consumer {
                progressDialog.dismiss()
                HttpErrorHandler(context).accept(it)
            }
        )
    }

    /**
     */
    private val launchPlayStore = DialogInterface.OnClickListener { _, _ ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmeStore.ZXING_URL)))
    }

    /**
     */
    private val launchBarcodeScanner = View.OnClickListener {

        try {
            startActivityForResult(Intent(
                AcmeStore.ZXING_ACTIVITY
            ).putExtra(
                "SCAN_MODE", "PRODUCT_MODE"
            ), 0)
        } catch (ex: ActivityNotFoundException) {
            AcmeDialogs.buildYesNo(activity, R.string.mainActivity_prompt, launchPlayStore).show()
        }
    }

    /**
     */
    override fun onActivityResult(request: Int, result: Int, data: Intent?) {

        if (data == null || request != AcmeStore.REQUEST_SCAN || result != Activity.RESULT_OK) {
            return
        }

        val format = data.getStringExtra("SCAN_RESULT_FORMAT")

        if (format == "UPC_A") {
            fetchProduct(data.getStringExtra("SCAN_RESULT"))
        } else {
            AcmeDialogs.buildOk(context, R.string.shoppingCart_invalidQr, format).show()
        }
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_progressLoading)

        shoppingCart_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        if (shoppingCart_recyclerView.adapter == null) {
            adapter = ShoppingCartAdapter(this)
            shoppingCart_recyclerView.adapter = adapter
        }

        shoppingCart_scan.setOnClickListener(launchBarcodeScanner)
    }
}