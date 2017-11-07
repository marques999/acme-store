package org.marques999.acme.store.products

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.orders.OrderProduct
import org.marques999.acme.store.view.ViewType
import org.marques999.acme.store.view.ViewTypeAdapter
import org.marques999.acme.store.view.RecyclerLoadingAdapter

import android.view.ViewGroup

class ShoppingCartAdapter(
    listener: ProductAdapter.ProductFragmentListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = ArrayList<ViewType>()
    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>()

    /**
     */
    private val loadingItem = object : ViewType {
        override fun getViewType() = ViewType.LOADING
    }

    /**
     */
    init {
        delegateAdapters.put(ViewType.LOADING, RecyclerLoadingAdapter())
        delegateAdapters.put(ViewType.PRODUCTS, ProductAdapter(listener))
    }

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

    /**
     */
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int) = items[position].getViewType()

    /**
     */
    fun beginLoading() {
        items.add(loadingItem)
        notifyItemInserted(items.size - 1)
    }

    /**
     */
    private fun endLoading(loadingPosition: Int) {
        items.removeAt(loadingPosition)
        notifyItemRemoved(loadingPosition)
    }

    /**
     */
    fun insertProduct(orderProduct: OrderProduct) {
        endLoading(items.size - 1)
        items.add(orderProduct)
        notifyItemInserted(items.size - 1)
    }

    /**
     */
    private fun removeProduct(productIndex: Int) {
        items.removeAt(productIndex)
        notifyItemRemoved(productIndex)
    }

    /**
     */
    fun removeProduct(orderProduct: OrderProduct) {
        removeProduct(items.indexOf(orderProduct))
    }

    /**
     */
    fun updateQuantity(orderProduct: OrderProduct) {
        notifyItemChanged(items.indexOf(orderProduct))
    }
}