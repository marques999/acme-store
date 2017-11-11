package org.marques999.acme.printer.orders

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import kotlinx.android.synthetic.main.fragment_order.view.*

import org.marques999.acme.printer.R
import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.views.ViewTypeAdapter
import org.marques999.acme.printer.views.ViewUtils

class OrderAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as OrderViewHolder).bind(item as Order)
    }

    inner class OrderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_order)
    ) {

        fun bind(item: Order) {
            itemView.order_token.text = item.token
            itemView.order_count.text = item.count.toString()
            itemView.order_total.text = ViewUtils.formatCurrency(item.total)
            itemView.order_createdAt.text = ViewUtils.formatDateTime(item.created_at)
            itemView.order_modifiedAt.text = ViewUtils.formatDateTime(item.updated_at)
        }
    }
}