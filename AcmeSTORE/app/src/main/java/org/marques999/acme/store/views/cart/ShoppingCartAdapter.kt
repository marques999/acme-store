package org.marques999.acme.store.views.cart

import com.squareup.picasso.Picasso

import android.view.ViewGroup
import android.view.LayoutInflater

import kotlinx.android.synthetic.main.fragment_cart_item.view.*

import org.marques999.acme.store.R
import org.marques999.acme.store.views.ViewType
import org.marques999.acme.store.views.ViewUtils
import org.marques999.acme.store.model.OrderProduct

import android.support.v7.widget.RecyclerView

class ShoppingCartAdapter(
    private val listener: ShoppingCartListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = ArrayList<OrderProduct>()

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
    override fun getItemViewType(position: Int) = ViewType.SHOPPING_CART_PRODUCT

    /**
     */
    inner class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_cart_item, parent, false
        )
    ) {

        fun bind(item: OrderProduct) {

            val itemBarcode = item.product.barcode

            itemView.setOnClickListener {
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
                R.string.shoppingCart_name, item.product.brand, item.product.name
            )

            itemView.product_barcode.text = itemBarcode
            itemView.shoppingCart_quantity.text = item.quantity.toString()
            itemView.product_price.text = ViewUtils.formatCurrency(item.product.price)

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