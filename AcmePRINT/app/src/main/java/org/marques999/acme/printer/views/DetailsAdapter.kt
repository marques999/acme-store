package org.marques999.acme.printer.views

import android.view.ViewGroup

import org.marques999.acme.printer.model.Order

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

import org.marques999.acme.printer.model.ViewType
import org.marques999.acme.printer.model.ViewTypeAdapter

class DetailsAdapter(val order: Order) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = arrayListOf<ViewType>(
        order.customer,
        order.customer.credit_card,
        order,
        *order.products.toTypedArray()
    )

    /**
     */
    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>()

    /**
     */
    init {
        delegateAdapters.put(ViewType.DETAILS_ORDER, DetailsActivityOrderAdapter())
        delegateAdapters.put(ViewType.DETAILS_PRODUCT, DetailsActivityProductAdapter())
        delegateAdapters.put(ViewType.DETAILS_CUSTOMER, DetailsActivityCustomerAdapter())
        delegateAdapters.put(ViewType.DETAILS_CREDIT_CARD, DetailsActivityCardAdapter())
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