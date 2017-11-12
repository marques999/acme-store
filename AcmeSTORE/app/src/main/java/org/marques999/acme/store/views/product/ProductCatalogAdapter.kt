package org.marques999.acme.store.views.product

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import com.squareup.picasso.Picasso

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import kotlinx.android.synthetic.main.fragment_catalog_item.view.*

import org.marques999.acme.store.model.Product
import org.marques999.acme.store.model.inflate

class ProductCatalogAdapter(
    private val items: List<Product>,
    private val listener: ProductCatalogListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    override fun getItemCount() = items.size

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductCatalogViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductCatalogViewHolder).bind(items[position])
    }

    /**
     */
    inner class ProductCatalogViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_catalog_item)
    ) {

        fun bind(item: Product) {

            itemView.catalog_container.setOnClickListener {
                listener.onItemSelected(item)
            }

            itemView.catalog_purchase.setOnClickListener {
                listener.onItemPurchased(item)
            }

            itemView.catalog_name.text = itemView.context.getString(
                R.string.product_name, item.brand, item.name
            )

            itemView.catalog_barcode.text = item.barcode
            itemView.catalog_price.text = AcmeUtils.formatCurrency(item.price)

            Picasso.with(itemView.context).load(
                item.image_uri
            ).fit().centerInside().into(
                itemView.catalog_photo
            )
        }
    }
}