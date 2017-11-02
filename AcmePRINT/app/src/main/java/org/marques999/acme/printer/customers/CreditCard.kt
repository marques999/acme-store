package org.marques999.acme.printer.customers

import org.marques999.acme.printer.views.ViewType

class CreditCard(
    val type: String,
    val number: String,
    val validity: java.util.Date
) : ViewType {
    override fun getViewType(): Int = ViewType.CREDIT_CARD
}