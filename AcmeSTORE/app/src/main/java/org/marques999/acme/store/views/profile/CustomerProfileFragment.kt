package org.marques999.acme.store.views.profile

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.AcmeDialogs

import kotlinx.android.synthetic.main.fragment_profile.*

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.order.OrderConfirmActivity

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CustomerProfileFragment : MainActivityFragment(R.layout.fragment_profile) {

    /**
     */
    private lateinit var acme: AcmeStore
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    override fun onRefresh() {

        progressDialog.show()

        acme.api.getCustomer(true).observeOn(
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

        acme.api.getCustomer(false).observeOn(
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
        acme = activity.application as AcmeStore
        progressDialog = AcmeDialogs.buildProgress(context, R.string.login_progressDialog)
    }

    /**
     */
    private val onFetchCustomer = Consumer<CustomerJSON> {

        progressDialog.dismiss()
        profile_customerName.text = it.name
        profile_customerNif.text = it.tax_number
        profile_customerAddress1.text = it.address1
        profile_customerAddress2.text = it.address2
        profile_customerUsername.text = it.username
        profile_privateKey.text = acme.privateKey
        profile_customerCreated.text = AcmeUtils.formatDateTime(it.created_at)
        profile_customerUpdated.text = AcmeUtils.formatDateTime(it.updated_at)
        OverScrollDecoratorHelper.setUpOverScroll(profile_scrollView)

        it.credit_card.let {
            profile_cardType.text = it.type
            profile_cardNumber.text = it.number
            profile_cardValidity.text = AcmeUtils.formatDate(it.validity)
        }

        profile_qrTest.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}