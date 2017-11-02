package org.marques999.acme.api

import io.reactivex.Observable
import org.marques999.acme.model.*

class AuthenticationService(private val apiService: AuthenticationApi) {

    fun register(
        name: String,
        username: String,
        email: String,
        address: String,
        password: String,
        nif: String,
        country: String,
        credit_card: CreditCard
    ): Observable<Customer> = apiService.register(
        CustomerPOST(name, email, username, password, address, nif, country, credit_card)
    )

    fun login(username: String, password: String): Observable<Token> {
        return apiService.login(Authentication(username, password))
    }
}