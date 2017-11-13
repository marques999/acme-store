package org.marques999.acme.store.views.customer

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeUtils

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.model.inflate
import org.marques999.acme.store.model.CreditCardJSON
import org.marques999.acme.store.model.ViewType
import org.marques999.acme.store.model.ViewTypeAdapter

import kotlinx.android.synthetic.main.fragment_profile_card.view.*

class ProfileFragmentCardAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CreditCardViewHolder(parent)
    }

    /**
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CreditCardViewHolder).bind(item as CreditCardJSON)
    }

    /**
     */
    inner class CreditCardViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.fragment_profile_card)
    ) {

        fun bind(item: CreditCardJSON) {
            itemView.profile_cardType.text = item.type
            itemView.profile_cardNumber.text = item.number
            itemView.profile_cardValidity.text = AcmeUtils.formatDate(item.validity)
        }
    }
}