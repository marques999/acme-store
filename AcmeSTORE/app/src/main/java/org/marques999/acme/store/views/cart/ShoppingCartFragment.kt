package org.marques999.acme.store.views.cart

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.AcmeDialogs

import android.content.Intent
import android.content.DialogInterface
import android.content.ActivityNotFoundException

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.net.Uri
import android.os.Bundle
import android.app.Activity
import android.app.ProgressDialog

import kotlinx.android.synthetic.main.fragment_cart.*

import org.marques999.acme.store.model.Product
import org.marques999.acme.store.model.CustomerCart

import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.views.main.MainActivityFragment
import org.marques999.acme.store.views.product.ProductViewActivity

class ShoppingCartFragment : MainActivityFragment(R.layout.fragment_cart), ShoppingCartListener {

    /**
     */
    override fun onRefresh() = Unit

    /**
     */
    private lateinit var shoppingCart: CustomerCart
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private val launchPlayStore = DialogInterface.OnClickListener { _, _ ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmeStore.ZXING_URL)))
    }

    /**
     */
    private val confirmPurchase = DialogInterface.OnClickListener { _, _ ->

        (activity.application as AcmeStore).api.insertOrder(
            shoppingCart.convertJson()
        ).observeOn(
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
    override fun onItemChanged() = shoppingCart.calculate().let {
        cart_quantity.text = it.first.toString()
        cart_subtotal.text = AcmeUtils.formatCurrency(it.second)
        shoppingCart_checkout.isEnabled = shoppingCart.notEmpty()
    }

    /**
     */
    override fun onItemSelected(product: Product) = startActivity(Intent(
        activity, ProductViewActivity::class.java
    ).putExtra(
        ProductViewActivity.EXTRA_PRODUCT, product
    ))

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingCart = (activity.application as AcmeStore).shoppingCart
        progressDialog = AcmeDialogs.buildProgress(context, R.string.global_progressLoading)
    }

    /**
     */
    fun registerPurchase(product: Product) {
        (shoppingCart_recyclerView.adapter as ShoppingCartAdapter).upsertItem(product)
    }

    /**
     */
    private fun fetchProduct(barcode: String) {

        progressDialog.show()

        (activity.application as AcmeStore).api.getProduct(barcode).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe({
            registerPurchase(it)
            progressDialog.dismiss()
        }, {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
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

        shoppingCart_recyclerView.apply {
            setHasFixedSize(true)
            clearOnScrollListeners()
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingCartAdapter(shoppingCart, this@ShoppingCartFragment)
            onItemChanged()
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