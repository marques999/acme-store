package org.marques999.acme.store.model

class Customer(
    private val name: String,
    private val username: String,
    private val address1: String,
    private val address2: String,
    private val country: String,
    private val tax_number: String,
    private val credit_card: CreditCard,
    private val created_at: java.util.Date,
    private val modified_at: java.util.Date
)