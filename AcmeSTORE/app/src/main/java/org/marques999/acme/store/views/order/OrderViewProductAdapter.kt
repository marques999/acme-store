package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderProduct
import org.marques999.acme.store.views.ViewType
import org.marques999.acme.store.views.ViewUtils

import kotlinx.android.synthetic.main.fragment_product.view.*

class OrderViewProductAdapter(private val items: List<OrderProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(items[position])
    }

    /**
     */
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int) = ViewType.PRODUCTS

    /**
     */
    inner class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_product, parent, false
        )
    ) {

        fun bind(item: OrderProduct) {

            itemView.product_barcode.text = item.product.barcode
            itemView.product_price.text = ViewUtils.formatCurrency(item.product.price)

            itemView.product_total.text = ViewUtils.formatCurrency(
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