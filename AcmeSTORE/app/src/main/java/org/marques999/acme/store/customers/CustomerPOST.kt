package org.marques999.acme.store.customers

class CustomerPOST(
    val name: String,
    val username: String,
    val password: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val public_key: String,
    var credit_card: CreditCard
)