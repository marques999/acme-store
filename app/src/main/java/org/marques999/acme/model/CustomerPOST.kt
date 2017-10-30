package org.marques999.acme.model

data class CustomerPOST(
    private val name: String,
    private val email: String,
    private val username: String,
    private val password: String,
    private val address: String,
    private val nif: String,
    private val country: String,
    private val credit_card: CreditCard
)