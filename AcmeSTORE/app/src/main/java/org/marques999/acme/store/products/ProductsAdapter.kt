package org.marques999.acme.store.products

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.orders.Product
import org.marques999.acme.store.view.ViewType
import org.marques999.acme.store.view.ViewTypeAdapter

import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(val viewActions: OnViewSelectedListener) : ViewTypeAdapter {

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

            itemView.setOnClickListener {
                viewActions.onItemSelected(item.barcode)
            }

            itemView.product_barcode.text = item.barcode
            itemView.product_price.text = AcmeStore.formatCurrency(item.price)

            itemView.product_name.text = itemView.context.getString(
                R.string.product_name, item.brand, item.name
            )

            Picasso.with(itemView.context).load(
                item.image_uri
            ).fit().centerCrop().into(
                itemView.product_photo
            )
        }
    }
}