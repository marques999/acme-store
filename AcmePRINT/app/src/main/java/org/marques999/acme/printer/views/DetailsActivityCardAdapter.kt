package org.marques999.acme.printer.views

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.printer.R
import org.marques999.acme.printer.AcmeUtils
import org.marques999.acme.printer.model.CreditCard
import org.marques999.acme.printer.model.ViewType
import org.marques999.acme.printer.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_cc.view.*

class DetailsActivityCardAdapter : ViewTypeAdapter {

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
            itemView.cc_validity.text = AcmeUtils.formatDate(item.validity)
        }
    }
}