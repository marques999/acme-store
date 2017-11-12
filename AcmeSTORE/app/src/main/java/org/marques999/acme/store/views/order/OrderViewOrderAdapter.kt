package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R
import org.marques999.acme.store.model.OrderJSON

import kotlinx.android.synthetic.main.fragment_order_order.view.*

import org.marques999.acme.store.views.ViewType
import org.marques999.acme.store.views.ViewTypeAdapter
import org.marques999.acme.store.views.ViewUtils

class OrderViewOrderAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as OrderViewHolder).bind(item as OrderJSON)
    }

    /**
     */
    inner class OrderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_order_order)
    ) {

        fun bind(item: OrderJSON) {
            itemView.orderView_token.text = item.token
            itemView.orderView_count.text = item.count.toString()
            itemView.orderView_total.text = ViewUtils.formatCurrency(item.total)
            itemView.orderView_createdAt.text = ViewUtils.formatDateTime(item.created_at)
            itemView.orderView_modifiedAt.text = ViewUtils.formatDateTime(item.updated_at)
        }
    }
}