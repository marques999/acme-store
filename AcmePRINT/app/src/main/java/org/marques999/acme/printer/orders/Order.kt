package org.marques999.acme.printer.orders

import org.marques999.acme.printer.customers.Customer
import org.marques999.acme.printer.views.ViewType

class Order private constructor(
    val token: String,
    val count: Int,
    val total: Double,
    val status: Int,
    val customer: Customer,
    val products: Array<OrderProduct>,
    val created_at: java.util.Date,
    val updated_at: java.util.Date
) : java.io.Serializable, ViewType {
    override fun getViewType(): Int = ViewType.ORDER
}