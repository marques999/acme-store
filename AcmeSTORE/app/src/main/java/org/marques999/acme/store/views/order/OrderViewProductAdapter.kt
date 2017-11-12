package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import kotlinx.android.synthetic.main.fragment_order_product.view.*

import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

class OrderViewProductAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderProductViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as OrderProductViewHolder).bind(item as OrderProduct)
    }

    /**
     */
    inner class OrderProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_order_product)
    ) {

        fun bind(item: OrderProduct) {

            itemView.product_barcode.text = item.product.barcode
            itemView.product_price.text = AcmeUtils.formatCurrency(item.product.price)

            itemView.product_total.text = AcmeUtils.formatCurrency(
                item.product.price * item.quantity
            )

            itemView.product_quantity.text = itemView.context.getString(
                R.string.orderView_quantity,
                item.quantity
            )

            itemView.product_name.text = itemView.context.getString(
                R.string.orderView_productName,
                item.product.brand,
                item.product.name
            )

            Picasso.with(itemView.context).load(
                item.product.image_uri
            ).fit().centerCrop().into(
                itemView.product_photo
            )
        }
    }
}