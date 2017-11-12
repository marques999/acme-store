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
import android.view.LayoutInflater
import android.view.ViewGroup

import org.marques999.acme.store.model.OrderProductPOST

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.views.main.MainActivityFragment
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

    private fun recalculateCart () {

        var subtotal = 0.0
        var quantity = 0

        shoppingCart.values.forEach{
            subtotal += it.product.price * it.quantity
            quantity += it.quantity
        }

        cart_quantity.text = quantity.toString()
        cart_subtotal.text = subtotal.toString() + " €"
    }

    override fun onItemUpdated(barcode: String, delta: Int) {

        shoppingCart[barcode]?.apply {

            if (delta != 0 && (delta > 0 && quantity < 99) || (delta < 0 && quantity > 0)) {
                quantity += delta
                adapter.update(this)
            }
        }

        recalculateCart()

        shoppingCart_checkout.isEnabled = shoppingCart.isNotEmpty()

    }

    /**
     */
    override fun onItemDeleted(barcode: String) {
        shoppingCart.remove(barcode)?.let { adapter.remove(it) }

        recalculateCart()
        shoppingCart_checkout.isEnabled = shoppingCart.isNotEmpty()
    }

    /**
     */
    fun registerPurchase(product: Product) {

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
        recalculateCart()
        progressDialog.dismiss()
    }

    /**
     */
    override fun onItemSelected(barcode: String) {

        shoppingCart[barcode]?.let {

            startActivity(Intent(
                activity, ProductViewActivity::class.java
            ).putExtra(
                ProductViewActivity.EXTRA_PRODUCT, it.product
            ).putExtra(
                ProductViewActivity.EXTRA_PURCHASED, true
            ))
        }
    }

    /**
     */
    private val confirmPurchase = DialogInterface.OnClickListener { _, _ ->

        (activity.application as AcmeStore).api.insertOrder(shoppingCart.values.map {
            OrderProductPOST(it.quantity, it.product.barcode)
        }).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe({
            progressDialog.dismiss()
        }, {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
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

        shoppingCart_scan.setOnClickListener {

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

        shoppingCart_checkout.setOnClickListener {
            AcmeDialogs.buildYesNo(activity, R.string.shoppingCart_confirm, confirmPurchase).show()
        }
    }
}