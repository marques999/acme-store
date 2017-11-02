package org.marques999.acme.model

import java.util.Date

data class OrderJSON(
    val token: String,
    val count: Int,
    val total: Double,
    val status: Int,
    val customer: String,
    val products: List<OrderProduct>,
    val created_at: Date,
    val updated_at: Date
)