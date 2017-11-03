package org.marques999.acme.store.model

class Order(
    val token: String,
    val status: Int,
    val customer: String,
    val products: List<OrderProduct>,
    val created_at:  java.util.Date,
    val updated_at:  java.util.Date
)