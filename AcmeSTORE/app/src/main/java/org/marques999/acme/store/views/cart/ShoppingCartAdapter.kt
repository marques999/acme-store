package org.marques999.acme.store.views.cart

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import kotlinx.android.synthetic.main.fragment_cart_item.view.*

import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.OrderProduct

class ShoppingCartAdapter(
    private val listener: ShoppingCartListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = ArrayList<OrderProduct>()

    /**
     */
    override fun getItemCount(): Int = items.size

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShoppingCartViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ShoppingCartViewHolder).bind(items[position])
    }

    /**
     */
    inner class ShoppingCartViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_cart_item)
    ) {

        fun bind(item: OrderProduct) {

            val itemBarcode = item.product.barcode

            itemView.product_container.setOnClickListener {
                listener.onItemSelected(itemBarcode)
            }

            itemView.shoppingCart_delete.setOnClickListener {
                listener.onItemDeleted(itemBarcode)
            }

            itemView.shoppingCart_minus.setOnClickListener {
                listener.onItemUpdated(itemBarcode, -1)
            }

            itemView.shoppingCart_plus.setOnClickListener {
                listener.onItemUpdated(itemBarcode, +1)
            }

            itemView.product_name.text = itemView.context.getString(
                R.string.product_name, item.product.brand, item.product.name
            )

            itemView.product_barcode.text = itemBarcode
            itemView.shoppingCart_quantity.text = item.quantity.toString()
            itemView.product_price.text = AcmeUtils.formatCurrency(item.product.price)

            Picasso.with(itemView.context).load(
                item.product.image_uri
            ).fit().centerInside().into(
                itemView.product_photo
            )
        }
    }

    /**
     */
    fun insert(orderProduct: OrderProduct) {
        items.add(orderProduct)
        notifyItemInserted(items.size - 1)
    }

    /**
     */
    private fun remove(productIndex: Int) {
        items.removeAt(productIndex)
        notifyItemRemoved(productIndex)
    }

    /**
     */
    fun remove(orderProduct: OrderProduct) {
        remove(items.indexOf(orderProduct))
    }

    /**
     */
    fun update(orderProduct: OrderProduct) {
        notifyItemChanged(items.indexOf(orderProduct))
    }
}