package org.marques999.acme.printer.customers

import org.marques999.acme.printer.R

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.views.ViewTypeAdapter
import org.marques999.acme.printer.views.ViewUtils

import kotlinx.android.synthetic.main.fragment_cc.view.*

class CreditCardAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CreditCardViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CreditCardViewHolder).bind(item as CreditCard)
    }

    inner class CreditCardViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.fragment_cc)
    ) {

        fun bind(item: CreditCard) {

            itemView.cc_type.text = item.type
            itemView.cc_number.text = item.number

            itemView.cc_validity.text = itemView.context.getString(
                R.string.details_ccDate,
                ViewUtils.formatDate(item.validity)
            )
        }
    }
}