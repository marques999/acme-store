package org.marques999.acme.printer.model

class Session(private val jwt: SessionJwt, username: String) {

    init {
        io.jsonwebtoken.Jwts.parser().require(
            "id", username
        ).setSigningKey(
            "mieic@feup#2017".toByteArray()
        ).parseClaimsJws(jwt.token)
    }

    fun wrapToken() = "Bearer ${jwt.token}"
}