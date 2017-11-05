package org.marques999.acme.store.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.marques999.acme.store.R
import org.marques999.acme.store.catalog.ProductCatalogFragment.OnListFragmentInteractionListener

import android.support.v7.widget.RecyclerView
import android.widget.TextView

class ProductCatalogAdapter(
    private val products: List<ProductCatalogContent.DummyItem>,
    private val listener: OnListFragmentInteractionListener?
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

            mView.setOnClickListener {
                listener?.onListFragmentInteraction(holder.mItem)
            }
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