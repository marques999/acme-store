package org.marques999.acme.store.view

import android.support.v7.widget.RecyclerView

import android.view.ViewGroup

import org.marques999.acme.store.common.inflate
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.R

import kotlinx.android.synthetic.main.item_product.view.*

class ProductDelegateAdapter(val viewActions: OnViewSelectedListener) : ViewTypeAdapter {

    interface OnViewSelectedListener {
        fun onItemSelected(barcode: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as NewsViewHolder).bind(item as Product)
    }

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.item_product)
    ) {

        fun bind(item: Product) {

            itemView.description.text = "${item.brand} ${item.name}"
            itemView.barcode.text = item.barcode
            itemView.price.text = "${item.price} €"

            super.itemView.setOnClickListener {
                viewActions.onItemSelected(item.barcode)
            }
        }
    }
}