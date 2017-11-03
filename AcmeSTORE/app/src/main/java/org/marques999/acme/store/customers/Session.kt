package org.marques999.acme.store.customers

class Session(val customer: Customer, val expire: java.util.Date, val token: String)