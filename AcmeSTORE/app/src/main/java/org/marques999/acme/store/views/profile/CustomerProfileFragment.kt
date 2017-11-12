package org.marques999.acme.store.views.profile

import android.os.Bundle
import android.content.Intent
import android.support.v4.app.Fragment

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.ViewUtils
import org.marques999.acme.store.views.order.OrderConfirmActivity

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import kotlinx.android.synthetic.main.fragment_profile.*

class CustomerProfileFragment : Fragment() {

    /**
     */
    private lateinit var customer: CustomerJSON

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        R.layout.fragment_profile, container, false
    )

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
            profile_cardValidity.text = ViewUtils.formatDate(it.validity)
        }

        profile_qrTest.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}