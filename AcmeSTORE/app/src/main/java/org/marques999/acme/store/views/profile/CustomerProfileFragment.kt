package org.marques999.acme.store.views.profile

import android.os.Bundle
import android.content.Intent

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeUtils

import kotlinx.android.synthetic.main.fragment_profile.*

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.order.OrderConfirmActivity

class CustomerProfileFragment : MainActivityFragment(R.layout.fragment_profile) {

    /**
     */
    override fun onRefresh() {

        (activity.application as AcmeStore).acmeApi.getCustomer(true).observeOn(
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        (activity.application as AcmeStore).acmeApi.getCustomer(false).observeOn(
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
    private val onFetchCustomer = Consumer<CustomerJSON> {

        profile_customerName.text = it.name
        profile_customerNif.text = it.tax_number
        profile_customerAddress1.text = it.address1
        profile_customerAddress2.text = it.address2

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