package org.marques999.acme.store.customers

class CustomerPOST {
    lateinit var name: String
    lateinit var username: String
    lateinit var password: String
    lateinit var address1: String
    lateinit var address2: String
    lateinit var country: String
    lateinit var tax_number: String
    lateinit var public_key: String
    lateinit var credit_card: CreditCardJSON
}