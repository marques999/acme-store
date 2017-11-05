package org.marques999.acme.store.products

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent

import kotlinx.android.synthetic.main.fragment_cart.*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.app.Activity

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore

import android.net.Uri

import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.HttpErrorHandler

import android.os.Bundle

import org.marques999.acme.store.orders.OrderProduct

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

class ShoppingCartFragment : Fragment(), ProductAdapter.ProductFragmentListener {

    private val shoppingCart = HashMap<String, OrderProduct>()

    /**
     */
    override fun onItemUpdated(barcode: String, delta: Int) {

        shoppingCart[barcode]?.apply {
            quantity += delta
            (news_list.adapter as ShoppingCartAdapter).updateQuantity(this)
        }
    }

    /**
     */
    override fun onItemDeleted(barcode: String) {
        shoppingCart.remove(barcode)?.let {
            (news_list.adapter as ShoppingCartAdapter).removeProduct(it)
        }
    }

    /**
     */
    private lateinit var application: AcmeStore

    /**
     */
    private val onFetchProduct = Consumer<Product> {

        val orderProduct = OrderProduct(1, it)

        shoppingCart[it.barcode]?.let {
            orderProduct.quantity += it.quantity
            (news_list.adapter as ShoppingCartAdapter).removeProduct(it)
        }

        (news_list.adapter as ShoppingCartAdapter).insertProduct(orderProduct)
        shoppingCart.put(orderProduct.product.barcode, orderProduct)
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = activity.application as AcmeStore
    }

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(
        context
    ).inflate(R.layout.fragment_cart, container, false)

    /**
     */
    private fun fetchProduct(barcode: String) {

        (news_list.adapter as ShoppingCartAdapter).beginLoading()

        application.acmeApi.getProduct(barcode).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onFetchProduct, HttpErrorHandler(context)
        )
    }

    /**
     */
    private val launchPlayStore = DialogInterface.OnClickListener { _, _ ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmeStore.ZXING_URL)))
    }
    /**
     */
    private val scanProductBarcode = View.OnClickListener {

        try {
            launchBarcodeScanner()
        } catch (ex: ActivityNotFoundException) {
            AcmeDialogs.buildYesNo(activity, R.string.main_promptInstall, launchPlayStore).show()
        }
    }

    /**
     */
    private fun launchBarcodeScanner() = startActivityForResult(Intent(
        AcmeStore.ZXING_ACTIVITY
    ).putExtra(
        "SCAN_MODE", "PRODUCT_MODE"
    ), 0)

    /**
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data == null || requestCode != 0 || resultCode != Activity.RESULT_OK) {
            return
        }

        val format = data.getStringExtra("SCAN_RESULT_FORMAT")

        if (format == "UPC_A") {
            fetchProduct(data.getStringExtra("SCAN_RESULT"))
        }
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        if (news_list.adapter == null) {
            news_list.adapter = ShoppingCartAdapter(this)
        }

        shoppingCart_scan.setOnClickListener(scanProductBarcode)
    }
}