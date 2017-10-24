package org.marques999.acme.model

import java.util.Date

data class Customer(
    val id: Int,
    val created: Date,
    val modified: Date,
    val name: String,
    val username: String,
    val email: String,
    val address: String,
    val nif: String,
    val country: String,
    val credit_card: CreditCard
)