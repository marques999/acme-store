package org.marques999.acme.store.orders

import org.marques999.acme.store.customers.Customer

class OrderJSON private constructor(
    val count: Int,
    val created_at: java.util.Date,
    val customer: Customer,
    val products: List<OrderProduct>,
    val status: Int,
    val token: String,
    val total: Double,
    val updated_at: java.util.Date
)