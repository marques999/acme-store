package org.marques999.acme.model

import java.util.Date

data class CreditCard(
    val id: Int,
    val type: String,
    val number: String,
    val validity: Date
)