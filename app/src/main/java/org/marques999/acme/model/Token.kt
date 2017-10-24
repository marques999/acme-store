package org.marques999.acme.model

import java.util.Date

data class Token(
    val expire: Date,
    val token: String
)