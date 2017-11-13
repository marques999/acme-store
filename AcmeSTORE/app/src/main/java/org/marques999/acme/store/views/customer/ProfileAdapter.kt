package org.marques999.acme.store.views.customer

import android.view.ViewGroup

import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter
import org.marques999.acme.store.model.CustomerJSON

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

class ProfileAdapter(val customer: CustomerJSON) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = arrayListOf(customer, customer.credit_card)

    /**
     */
    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>().apply {
        put(ViewType.PROFILE_CUSTOMER, ProfileFragmentCustomerAdapter())
        put(ViewType.PROFILE_CREDIT_CARD, ProfileFragmentCardAdapter())
        notifyItemRangeChanged(0, items.size)
    }

    /**
     */
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int) = items[position].getViewType()

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }
}