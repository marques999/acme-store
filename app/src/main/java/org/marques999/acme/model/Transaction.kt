package org.marques999.acme.model

import java.util.Date

data class Transaction(
    val id: Int,
    val created: Date,
    val modified: Date,
    val customer: String,
    val total: Double,
    val valid: Boolean,
    val products: List<Product>
)