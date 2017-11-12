package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.view.LayoutInflater

import kotlinx.android.synthetic.main.fragment_history_item.view.*

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils
import org.marques999.acme.store.model.Order
import org.marques999.acme.store.model.ViewType

import android.support.v7.widget.RecyclerView

class OrderHistoryAdapter(
    private val listener: OrderHistoryListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     */
    private val items = ArrayList<Order>()

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
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_history_item, parent, false
        )
    ) {

        fun bind(item: Order) {
            itemView.history_token.text = item.token
            itemView.history_createdAt.text = AcmeUtils.formatDateTime(item.created_at)
            itemView.setOnClickListener { listener.onItemSelected(item.token) }
        }
    }

    /**
     */
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int) = ViewType.ORDER_HISTORY_ORDER

    /**
     */
    fun refreshItems(orders: List<Order>) {
        notifyItemRangeRemoved(0, items.size)
        items.clear()
        items.addAll(orders)
        notifyItemRangeInserted(0, orders.size)
    }
}