package org.marques999.acme.store.views.catalog

import android.view.LayoutInflater
import android.view.ViewGroup

import org.marques999.acme.store.R

import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_catalog_item.view.*
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.views.order.CatalogListener

class ProductCatalogAdapter(
    private val listener: CatalogListener,
    private val productlistener: ProductCatalogListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatalogViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CatalogViewHolder).bind(items[position])
    }

    inner class CatalogViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_catalog_item, parent, false
            )
    ) {
        fun bind(item: Product) {
            itemView.item_name.text = item.name
            itemView.item_price.text = item.price.toString()
            itemView.setOnClickListener { listener.onItemSelected(item) }
        }
    }

    fun refreshItems(orders: List<Product>) {
        notifyItemRangeRemoved(0, items.size)
        items.clear()
        items.addAll(orders)
        notifyItemRangeInserted(0, orders.size)
    }

    override fun getItemCount() = items.size
}