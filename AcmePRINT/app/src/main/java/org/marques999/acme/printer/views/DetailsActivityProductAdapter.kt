package org.marques999.acme.printer.views

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.printer.R
import org.marques999.acme.printer.AcmeUtils
import org.marques999.acme.printer.model.OrderProduct
import org.marques999.acme.printer.model.ViewType
import org.marques999.acme.printer.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_product.view.*

class DetailsActivityProductAdapter : ViewTypeAdapter {

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
            itemView.product_quantity.text =  item.quantity.toString()
            itemView.product_price.text = AcmeUtils.formatCurrency(item.product.price)

            itemView.product_total.text = AcmeUtils.formatCurrency(
                item.product.price * item.quantity
            )

            itemView.product_name.text = itemView.context.getString(
                R.string.product_name,
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