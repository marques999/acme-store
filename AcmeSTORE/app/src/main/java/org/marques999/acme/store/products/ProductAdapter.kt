package org.marques999.acme.store.products

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.view.ViewType
import org.marques999.acme.store.view.ViewTypeAdapter

import kotlinx.android.synthetic.main.item_product.view.*
import org.marques999.acme.store.orders.OrderProduct

class ProductAdapter(val viewActions: ProductFragmentListener) : ViewTypeAdapter {

    interface ProductFragmentListener {
        fun onItemDeleted(barcode: String)
        fun onItemUpdated(barcode: String, delta: Int)
        fun onItemSelected(barcode: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as ProductViewHolder).bind(item as OrderProduct)
    }

    inner class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.item_product)
    ) {

        fun bind(item: OrderProduct) {

            val itemBarcode = item.product.barcode

            itemView.setOnClickListener {
                viewActions.onItemSelected(itemBarcode)
            }

            itemView.shoppingCart_delete.setOnClickListener {
                viewActions.onItemDeleted(itemBarcode)
            }

            itemView.shoppingCart_minus.setOnClickListener {
                viewActions.onItemUpdated(itemBarcode, -1)
            }

            itemView.shoppingCart_plus.setOnClickListener {
                viewActions.onItemUpdated(itemBarcode, +1)
            }

            itemView.product_barcode.text = itemBarcode
            itemView.product_price.text = AcmeStore.formatCurrency(item.product.price)
            itemView.shoppingCart_quantity.text = item.quantity.toString()
            itemView.product_name.text = itemView.context.getString(
                R.string.shoppingCart_name, item.product.brand, item.product.name
            )

            Picasso.with(itemView.context).load(
                item.product.image_uri
            ).fit().centerInside().into(
                itemView.product_photo
            )
        }
    }
}