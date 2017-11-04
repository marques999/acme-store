package org.marques999.acme.printer.customers

import org.marques999.acme.printer.views.ViewType

class CreditCard private constructor(
    val type: String,
    val number: String,
    val validity: java.util.Date
) : java.io.Serializable, ViewType {
    override fun getViewType(): Int = ViewType.CREDIT_CARD
}