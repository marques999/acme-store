package org.marques999.acme.store.catalog

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.R

import android.widget.TextView
import android.support.v7.widget.RecyclerView

class ProductCatalogAdapter(
    private val products: List<ProductCatalogContent.DummyItem>,
    private val listener: ProductCatalogListener
) : RecyclerView.Adapter<ProductCatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.fragment_dummy, parent, false
        )
    )

    /**
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            mItem = products[position]
            mIdView.text = products[position].id
            mContentView.text = products[position].content
            mContentView.setOnClickListener { listener.onPurchase("dummy") }
        }
    }

    /**
     */
    override fun getItemCount() = products.size

    /**
     */
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        /**
         */
        lateinit var mItem: ProductCatalogContent.DummyItem

        /**
         */
        val mIdView: TextView = mView.findViewById(R.id.id)
        val mContentView: TextView = mView.findViewById(R.id.content)

        /**
         */
        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}