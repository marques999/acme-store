package org.marques999.acme.view

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

import android.view.ViewGroup

import org.marques999.acme.common.ViewType
import org.marques999.acme.common.ViewTypeAdapter
import org.marques999.acme.model.Product

class ProductAdapter(
    listener: ProductDelegateAdapter.OnViewSelectedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val loadingItem = object : ViewType {
        override fun getViewType() = ViewType.LOADING
    }

    private val delegateAdapters = SparseArrayCompat<ViewTypeAdapter>()
    private val items: ArrayList<ViewType> = arrayListOf(loadingItem)

    init {
        delegateAdapters.put(ViewType.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ViewType.PRODUCTS, ProductDelegateAdapter(listener))
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addNews(news: List<Product>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        items.addAll(news)
        notifyItemRangeChanged(initPosition, items.size)
    }

    fun clearAndAddNews(news: List<Product>) {
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