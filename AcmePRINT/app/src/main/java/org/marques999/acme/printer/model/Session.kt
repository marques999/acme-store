package org.marques999.acme.printer.model

import org.marques999.acme.printer.AcmePrinter

class Session(private val jwt: SessionJwt, username: String) {

    /**
     */
    fun wrapToken() = "Bearer ${jwt.token}"

    /**
     */
    init {
        io.jsonwebtoken.Jwts.parser().require(
            "id", username
        ).setSigningKey(
            AcmePrinter.RAMEN_RECIPE
        ).parseClaimsJws(jwt.token)
    }
}