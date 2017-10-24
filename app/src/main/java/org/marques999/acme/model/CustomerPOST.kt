package org.marques999.acme.model

data class CustomerPOST(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val address: String,
    val nif: String,
    val country: String,
    val credit_card: CreditCard
)