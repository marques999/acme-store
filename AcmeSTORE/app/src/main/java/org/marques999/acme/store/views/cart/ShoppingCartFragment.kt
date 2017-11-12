package org.marques999.acme.store.views.cart

import android.content.Intent
import android.content.DialogInterface
import android.content.ActivityNotFoundException

import kotlinx.android.synthetic.main.fragment_cart.*

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.net.Uri
import android.os.Bundle
import android.app.Activity
import android.app.ProgressDialog

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.views.product.ProductViewActivity

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

class ShoppingCartFragment : Fragment(), ShoppingCartListener {

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
            quantity += delta
            adapter.update(this)
        }
    }

    /**
     */
    override fun onItemDeleted(barcode: String) {
        shoppingCart.remove(barcode)?.let { adapter.remove(it) }
    }

    /**
     */
    private val onFetchProduct = Consumer<Product> {

        val orderProduct = OrderProduct(1, it)

        shoppingCart[it.barcode]?.let {
            orderProduct.quantity += it.quantity
            adapter.remove(it)
        }

        adapter.insert(orderProduct)
        shoppingCart.put(orderProduct.product.barcode, orderProduct)
        progressDialog.dismiss()
    }

    /**
     */
    override fun onItemSelected(barcode: String) {

        shoppingCart[barcode]?.let {

            startActivity(Intent(
                activity, ProductViewActivity::class.java
            ).putExtra(
                ProductViewActivity.ORDER_PRODUCT, it
            ).putExtra(
                ProductViewActivity.ORDER_ACTIVE, true
            ))
        }
    }

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        R.layout.fragment_cart, container, false
    )

    /**
     */
    private fun fetchProduct(barcode: String) {

        progressDialog.show()

        (activity.application as AcmeStore).acmeApi.getProduct(barcode).observeOn(
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
            AcmeDialogs.buildYesNo(activity, R.string.main_promptInstall, launchPlayStore).show()
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

        cart_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        if (cart_recyclerView.adapter == null) {
            adapter = ShoppingCartAdapter(this)
            cart_recyclerView.adapter = adapter
        }

        shoppingCart_scan.setOnClickListener(launchBarcodeScanner)
    }
}