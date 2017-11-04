package org.marques999.acme.store.dummy

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.marques999.acme.store.R
import org.marques999.acme.store.dummy.OrderContent.DummyItem

import android.widget.TextView

class OrderAdapter(private val mValues: List<DummyItem>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    /**
     */
    override fun getItemCount() = mValues.size

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
    )

    /**
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content
    }

    /**
     */
    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        /**
         */
        lateinit var mItem: DummyItem

        /**
         */
        val mIdView: TextView = mView.findViewById(R.id.id)

        /**
         */
        val mContentView: TextView = mView.findViewById(R.id.content)

        /**
         */
        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}