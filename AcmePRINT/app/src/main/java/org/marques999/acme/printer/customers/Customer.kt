package org.marques999.acme.printer.customers

import org.marques999.acme.printer.views.ViewType

class Customer private constructor(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: java.util.Date,
    val modified_at: java.util.Date
) : java.io.Serializable, ViewType {
    override fun getViewType(): Int = ViewType.CUSTOMER
}