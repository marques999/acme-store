package org.marques999.acme.store.view

import org.marques999.acme.store.orders.Product

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.products.ProductsAdapter
import org.marques999.acme.store.products.ProductsLoadingAdapter

import android.view.ViewGroup

class RecyclerAdapter(
    listener: ProductsAdapter.OnViewSelectedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val loadingItem = object : ViewType {
        override fun getViewType() = ViewType.LOADING
    }

    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>()
    private val items: ArrayList<ViewType> = arrayListOf(loadingItem)

    init {
        delegateAdapters.put(ViewType.LOADING, ProductsLoadingAdapter())
        delegateAdapters.put(ViewType.PRODUCTS, ProductsAdapter(listener))
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addProducts(news: List<Product>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        items.addAll(news)
        notifyItemRangeChanged(initPosition, items.size)
    }

    fun clearAndAddProducts(news: List<Product>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    private fun getLastPosition() = if (items.lastIndex == -1) {
        0
    } else {
        items.lastIndex
    }
}