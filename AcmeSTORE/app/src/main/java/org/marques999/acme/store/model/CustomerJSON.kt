package org.marques999.acme.store.model

class CustomerJSON {
    lateinit var name: String
    lateinit var username: String
    lateinit var address1: String
    lateinit var address2: String
    lateinit var country: String
    lateinit var tax_number: String
    lateinit var credit_card: CreditCardJSON
    lateinit var created_at: java.util.Date
    lateinit var updated_at: java.util.Date
}