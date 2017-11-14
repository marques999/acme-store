package org.marques999.acme.store.views.cart

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import kotlinx.android.synthetic.main.fragment_cart_item.view.*

import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.Product
import org.marques999.acme.store.model.CustomerCart
import org.marques999.acme.store.model.OrderProduct

import java.util.ArrayList

class ShoppingCartAdapter(
    private val shoppingCart: CustomerCart,
    private val shoppingCartListener: ShoppingCartListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    override fun getItemCount(): Int = items.size

    /**
     */
    private val items = ArrayList<OrderProduct>(shoppingCart.getProducts()).apply {
        notifyItemRangeInserted(0, size)
    }

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

                shoppingCart[itemBarcode]?.let {
                    shoppingCartListener.onItemSelected(it.product)
                }
            }

            itemView.shoppingCart_delete.setOnClickListener {

                shoppingCart.delete(itemBarcode)?.let {
                    deleteItem(it)
                    shoppingCartListener.onItemChanged()
                }
            }

            itemView.shoppingCart_minus.setOnClickListener {
                updateItem(itemBarcode, -1)
            }

            itemView.shoppingCart_plus.setOnClickListener {
                updateItem(itemBarcode, 1)
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
    fun clearItems() {
        shoppingCart.checkout()
        notifyItemRangeRemoved(0, items.size)
        shoppingCartListener.onItemChanged()
    }

    /**
     */
    private fun updateItem(barcode: String, delta: Int) {

        shoppingCart.update(barcode, delta)?.let {
            notifyItemChanged(items.indexOf(it))
            shoppingCartListener.onItemChanged()
        }
    }

    /**
     */
    private fun deleteItem(orderProduct: OrderProduct) {

        items.indexOf(orderProduct).let {
            items.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    /**
     */
    fun upsertItem(product: Product) {

        shoppingCart[product.barcode]?.let {
            deleteItem(it)
        }

        shoppingCart.insert(product).let {
            items.add(it)
            notifyItemInserted(items.lastIndex)
            shoppingCartListener.onItemChanged()
        }
    }
}