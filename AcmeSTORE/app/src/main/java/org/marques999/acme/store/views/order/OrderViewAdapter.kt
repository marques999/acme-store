package org.marques999.acme.store.views.order

import android.view.ViewGroup

import org.marques999.acme.store.model.QrCode
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

class OrderViewAdapter(
    val order: OrderJSON,
    qrCode: QrCode,
    private val listener: OrderViewListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = arrayListOf(
        order, *order.products.toTypedArray(), qrCode
    )

    /**
     */
    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>().apply {
        put(ViewType.ORDER_VIEW_CODE, OrderViewCodeAdapter())
        put(ViewType.ORDER_VIEW_ORDER, OrderViewOrderAdapter())
        put(ViewType.ORDER_VIEW_PRODUCT, OrderViewProductAdapter(listener))
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