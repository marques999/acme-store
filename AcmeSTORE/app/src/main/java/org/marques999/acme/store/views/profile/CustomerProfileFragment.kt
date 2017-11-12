package org.marques999.acme.store.views.profile

import android.os.Bundle
import android.content.Intent

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeUtils

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import kotlinx.android.synthetic.main.fragment_profile.*

import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.MainActivityFragment
import org.marques999.acme.store.views.order.OrderConfirmActivity

class CustomerProfileFragment : MainActivityFragment(R.layout.fragment_profile) {

    /**
     */
    override fun onRefresh() {
        AcmeDialogs.buildOk(activity, R.string.actionBar_profile).show()
    }

    /**
     */
    private lateinit var customer: CustomerJSON

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customer = (activity.application as AcmeStore).acmeApi.getCustomer()
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        profile_customerName.text = customer.name
        profile_customerNif.text = customer.tax_number
        profile_customerAddress1.text = customer.address1
        profile_customerAddress2.text = customer.address2

        customer.credit_card.let {
            profile_cardType.text = it.type
            profile_cardNumber.text = it.number
            profile_cardValidity.text = AcmeUtils.formatDate(it.validity)
        }

        profile_qrTest.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}