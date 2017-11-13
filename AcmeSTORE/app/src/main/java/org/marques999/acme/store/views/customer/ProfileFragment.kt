package org.marques999.acme.store.views.customer

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs

import kotlinx.android.synthetic.main.fragment_profile.*

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.main.MainActivityFragment
import org.marques999.acme.store.views.order.OrderConfirmActivity

class ProfileFragment : MainActivityFragment(R.layout.fragment_profile) {

    /**
     */
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    override fun onRefresh() {

        progressDialog.show()

        (activity.application as AcmeStore).api.getCustomer(true).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe({
            progressDialog.dismiss()
            onFetchCustomer.accept(it)
        }, {
            progressDialog.dismiss()
            HttpErrorHandler(context).accept(it)
        })
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        (activity.application as AcmeStore).api.getCustomer(false).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onFetchCustomer,
            HttpErrorHandler(context)
        )
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AcmeDialogs.buildProgress(context, R.string.login_progressDialog)
    }

    /**
     */
    private val onFetchCustomer = Consumer<CustomerJSON> {

        progressDialog.dismiss()

        profile_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = ProfileAdapter(it)
        }

        profile_logout.setOnClickListener {
            activity.onBackPressed()
        }

        profile_qrTest.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}