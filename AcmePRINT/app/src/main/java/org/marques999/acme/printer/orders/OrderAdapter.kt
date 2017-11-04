package org.marques999.acme.printer.orders

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_order.view.*

import org.marques999.acme.printer.R
import org.marques999.acme.printer.AcmePrinter
import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.views.ViewTypeAdapter

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
            itemView.order_count.text = item.count.toString()
            itemView.order_total.text = AcmePrinter.formatCurrency(item.total)
            itemView.order_date.text = AcmePrinter.formatDateTime(item.created_at)
        }
    }
}