package org.marques999.acme.store.common

import java.util.Calendar
import com.fasterxml.jackson.databind.ObjectMapper
import org.marques999.acme.store.customers.CustomerJSON
import com.fasterxml.jackson.databind.util.ISO8601DateFormat

class Session(private val jwt: SessionJwt, username: String) {

    /**
     */
    fun wrapToken() = "Bearer ${jwt.token}"

    /**
     */
    fun hasExpired() = jwt.expire.after(Calendar.getInstance().time)

    /**
     */
    val customer: CustomerJSON = ObjectMapper().setDateFormat(
        ISO8601DateFormat()
    ).convertValue(
        io.jsonwebtoken.Jwts.parser().require(
            "id", username
        ).setSigningKey(
            "mieic@feup#2017".toByteArray()
        ).parseClaimsJws(
            jwt.token
        ).body["customer", HashMap::class.java], CustomerJSON::class.java
    )
}