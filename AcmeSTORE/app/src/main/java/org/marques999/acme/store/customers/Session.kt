package org.marques999.acme.store.customers

import com.fasterxml.jackson.databind.ObjectMapper

class Session(private val jwt: Jwt, username: String) {

    /**
     */
    fun wrapToken() = "Bearer ${jwt.token}"

    /**
     */
    fun hasExpired() = jwt.expire.after(java.util.Calendar.getInstance().time)

    /**
     */
    val customer: CustomerJSON = ObjectMapper().convertValue(io.jsonwebtoken.Jwts.parser()
        .require("id", username)
        .setSigningKey("mieic@feup#2017".toByteArray())
        .parseClaimsJws(jwt.token).body["customer", HashMap::class.java], CustomerJSON::class.java
    )
}