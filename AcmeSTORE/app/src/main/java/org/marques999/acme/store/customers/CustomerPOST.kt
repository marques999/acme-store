package org.marques999.acme.store.customers

class CustomerPOST(
    private val name: String,
    private val username: String,
    private val password: String,
    private val address1: String,
    private val address2: String,
    private val country: String,
    private val tax_number: String,
    private val public_key: String,
    private val credit_card: CreditCard
)