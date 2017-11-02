package org.marques999.acme.model

import java.util.Date

data class Order(
    val token: String,
    val status: Int,
    val customer: String,
    val products: List<OrderProduct>,
    val created_at: Date,
    val updated_at: Date
)