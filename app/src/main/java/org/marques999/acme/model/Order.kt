package org.marques999.acme.model

data class Order(
    private val payload: String,
    private val signature: String
)