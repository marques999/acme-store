package org.marques999.acme.store.views.order

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R
import org.marques999.acme.store.model.QrCode
import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_order_code.view.*

class OrderViewCodeAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderBitmapViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as OrderBitmapViewHolder).bind(item as QrCode)
    }

    /**
     */
    inner class OrderBitmapViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_order_code)
    ) {
        fun bind(item: QrCode) = itemView.orderView_code.setImageBitmap(item.bitmap)
    }
}