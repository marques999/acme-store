package org.marques999.acme.store.common

import java.util.Calendar
import com.fasterxml.jackson.databind.ObjectMapper
import org.marques999.acme.store.customers.CustomerJSON

class Session(private val jwt: SessionJwt, username: String) {

    /**
     */
    fun wrapToken() = "Bearer ${jwt.token}"

    /**
     */
    fun hasExpired() = jwt.expire.after(Calendar.getInstance().time)

    /**
     */
    val customer: CustomerJSON = ObjectMapper().convertValue(io.jsonwebtoken.Jwts.parser()
        .require("id", username)
        .setSigningKey("mieic@feup#2017".toByteArray())
        .parseClaimsJws(jwt.token).body["customer", HashMap::class.java], CustomerJSON::class.java
    )
}