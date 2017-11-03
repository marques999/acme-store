package org.marques999.acme.store.customers

class Customer private constructor(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: java.util.Date,
    val updated_at: java.util.Date
)