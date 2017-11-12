package org.marques999.acme.printer.views

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import kotlinx.android.synthetic.main.fragment_customer.view.*
import org.marques999.acme.printer.R
import org.marques999.acme.printer.model.ViewType
import org.marques999.acme.printer.model.ViewTypeAdapter

import org.marques999.acme.printer.model.Customer

class DetailsActivityCustomerAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CustomerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CustomerViewHolder).bind(item as Customer)
    }

    inner class CustomerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_customer)
    ) {

        fun bind(item: Customer) {

            itemView.customer_name.text = item.name
            itemView.customer_address1.text = item.address1
            itemView.customer_address2.text = item.address2

            itemView.customer_nif.text = itemView.context.getString(
                R.string.details_customerTax,
                item.tax_number
            )
        }
    }
}