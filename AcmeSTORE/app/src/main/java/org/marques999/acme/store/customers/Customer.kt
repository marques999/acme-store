package org.marques999.acme.store.customers

class Customer(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: java.util.Date,
    val modified_at: java.util.Date
)