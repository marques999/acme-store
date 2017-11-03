package org.marques999.acme.store.view

import android.view.ViewGroup

import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.orders.Product
import org.marques999.acme.store.R

import kotlinx.android.synthetic.main.item_product.view.*

class ProductDelegateAdapter(val viewActions: OnViewSelectedListener) : ViewTypeAdapter {

    interface OnViewSelectedListener {
        fun onItemSelected(barcode: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as ProductViewHolder).bind(item as Product)
    }

    inner class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.item_product)
    ) {

        fun bind(item: Product) {
            itemView.description.text = "${item.brand} ${item.name}"
            itemView.barcode.text = item.barcode
            itemView.price.text = "${item.price} €"
            itemView.setOnClickListener { viewActions.onItemSelected(item.barcode) }
        }
    }
}