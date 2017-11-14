package org.marques999.acme.store.views.customer

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_profile_customer.view.*

class ProfileFragmentCustomerAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CustomerViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CustomerViewHolder).bind(item as CustomerJSON)
    }

    /**
     */
    inner class CustomerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_profile_customer)
    ) {

        fun bind(item: CustomerJSON) {
            itemView.profile_name.text = item.name
            itemView.profile_nif.text = item.tax_number
            itemView.profile_address1.text = item.address1
            itemView.profile_address2.text = item.address2
            itemView.profile_username.text = item.username
            itemView.profile_created.text = AcmeUtils.formatDateTime(item.created_at)
            itemView.profile_updated.text = AcmeUtils.formatDateTime(item.updated_at)
        }
    }
}