package org.marques999.acme.printer.products

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_product.view.*

import org.marques999.acme.printer.R
import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.views.ViewTypeAdapter
import org.marques999.acme.printer.orders.OrderProduct

class ProductAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as ProductViewHolder).bind(item as OrderProduct)
    }

    inner class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_product)
    ) {

        fun bind(item: OrderProduct) {
            itemView.product_barcode.text = item.product.barcode
            itemView.product_price.text = "${item.product.price} €"
            itemView.product_quantity.text = item.quantity.toString()
            itemView.product_photo.setImageURI(Uri.parse(item.product.image_uri))
            itemView.product_name.text = "${item.product.brand} ${item.product.name}"
        }
    }
}