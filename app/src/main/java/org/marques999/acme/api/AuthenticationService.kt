package org.marques999.acme.api

import io.reactivex.Observable
import org.marques999.acme.model.Authentication
import org.marques999.acme.model.Token

class AuthenticationService(private val apiService: AuthenticationApi) {

    fun login(username: String, password: String): Observable<Token> {
        return apiService.login(Authentication(username, password))
    }
}