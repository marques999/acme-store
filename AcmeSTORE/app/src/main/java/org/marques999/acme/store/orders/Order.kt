package org.marques999.acme.store.orders

class Order private constructor(
    val id: Int,
    val created_at: java.util.Date,
    val updated_at: java.util.Date,
    val status: Int,
    val count: Int,
    val total: Double,
    val customer: String,
    val token: String
)