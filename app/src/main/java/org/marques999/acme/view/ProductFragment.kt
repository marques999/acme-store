package org.marques999.acme.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.products_fragment.*

import org.marques999.acme.AcmeStore
import org.marques999.acme.common.HttpErrorHandler
import org.marques999.acme.R
import org.marques999.acme.common.RxBaseFragment
import org.marques999.acme.common.inflate
import org.marques999.acme.model.Product

class ProductFragment : RxBaseFragment(), ProductDelegateAdapter.OnViewSelectedListener {

    override fun onItemSelected(barcode: String) {

        application.acmeApi
            .getProduct(barcode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(viewProduct, HttpErrorHandler(context))
    }

    private lateinit var application: AcmeStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = activity.application as AcmeStore
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.products_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
        }

        initAdapter()

        application.acmeApi.getProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(refreshProducts, HttpErrorHandler(context))
    }

    private val viewProduct = Consumer<Product> {
        application.alerts.showOk(it.name, it.description)
    }

    private val refreshProducts = Consumer<List<Product>> {
        (news_list.adapter as ProductAdapter).addNews(it)
    }

    private fun initAdapter() {

        if (news_list.adapter == null) {
            news_list.adapter = ProductAdapter(this)
        }
    }
}