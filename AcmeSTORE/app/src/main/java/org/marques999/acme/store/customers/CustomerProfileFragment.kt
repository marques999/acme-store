package org.marques999.acme.store.customers

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_profile.*
import org.marques999.acme.store.orders.OrderConfirmActivity

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
        customerProfile_name.text = customer.name
        customerProfile_nif.text = customer.tax_number
        customerProfile_address1.text = customer.address1
        customerProfile_address2.text = customer.address2

        customerProfile_test.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}