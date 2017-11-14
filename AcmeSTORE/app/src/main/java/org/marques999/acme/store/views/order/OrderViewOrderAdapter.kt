package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_order_order.view.*

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
        parent.inflate(R.layout.fragment_order_order)
    ) {

        fun bind(item: OrderJSON) {
            itemView.orderView_token.text = item.token
            itemView.orderView_items.text = item.count.toString()
            itemView.orderView_orderTotal.text = AcmeUtils.formatCurrency(item.total)
            itemView.orderView_createdAt.text = AcmeUtils.formatDateTime(item.created_at)
            itemView.orderView_modifiedAt.text = AcmeUtils.formatDateTime(item.updated_at)
        }
    }
}