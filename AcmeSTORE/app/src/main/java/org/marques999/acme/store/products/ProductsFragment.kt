package org.marques999.acme.store.products

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_products.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.app.Fragment

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.orders.Product
import org.marques999.acme.store.view.RecyclerAdapter

import android.support.v7.widget.LinearLayoutManager

class ProductsFragment : Fragment(), ProductsAdapter.OnViewSelectedListener {

    /**
     */
    private lateinit var application: AcmeStore

    /**
     */
    private val onView = Consumer<Product> {
        AcmeDialogs.buildOk(context, it.name, it.description).show()
    }

    /**
     */
    private val onRefresh = Consumer<List<Product>> {
        (news_list.adapter as RecyclerAdapter).addProducts(it)
    }

    /**
     */
    override fun onItemSelected(barcode: String) {

        application.acmeApi.getProduct(barcode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onView, HttpErrorHandler(context))
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
    ).inflate(R.layout.fragment_products, container, false)

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        if (news_list.adapter == null) {
            news_list.adapter = RecyclerAdapter(this)
        }

        application.acmeApi.getProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onRefresh, HttpErrorHandler(context))
    }
}