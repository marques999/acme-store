package org.marques999.acme.store.views.profile

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.views.order.OrderConfirmActivity

import android.os.Bundle
import android.content.Intent
import android.support.v4.app.Fragment

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
        customerProfile_name.text = customer.name
        customerProfile_nif.text = customer.tax_number
        customerProfile_address1.text = customer.address1
        customerProfile_address2.text = customer.address2

        customerProfile_test.setOnClickListener {
            startActivity(Intent(context, OrderConfirmActivity::class.java))
        }
    }
}