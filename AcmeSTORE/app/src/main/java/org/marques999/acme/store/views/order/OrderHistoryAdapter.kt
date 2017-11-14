package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.model.Order
import org.marques999.acme.store.model.inflate

import kotlinx.android.synthetic.main.fragment_history_item.view.*

class OrderHistoryAdapter(
    private val listener: OrderHistoryListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = ArrayList<Order>()

    /**
     */
    override fun getItemCount(): Int = items.size

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrderViewHolder).bind(items[position])
    }

    /**
     */
    inner class OrderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_history_item)
    ) {

        fun bind(item: Order) {
            itemView.orderHistory_token.text = item.token
            itemView.orderHistory_total.text = AcmeUtils.formatCurrency(item.total)
            itemView.orderHistory_date.text = AcmeUtils.formatDateTime(item.created_at)
            itemView.setOnClickListener { listener.onItemSelected(item.token) }
        }
    }

    /**
     */
    fun refreshItems(orders: List<Order>) {
        notifyItemRangeRemoved(0, items.size)
        items.clear()
        items.addAll(orders)
        notifyItemRangeInserted(0, orders.size)
    }
}