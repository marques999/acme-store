package org.marques999.acme.api

class AuthenticationProvider {

    fun provideAuthentication(): AuthenticationService {
        return AuthenticationService(AcmeFactory.createUnauthorized())
    }
}