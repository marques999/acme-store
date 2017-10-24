package org.marques999.acme

import android.app.Application

import org.marques999.acme.api.AcmeService
import org.marques999.acme.api.AuthenticationProvider
import org.marques999.acme.api.AuthenticationService

class AcmeStore : Application() {
    lateinit var acmeApi: AcmeService
    val authenticationApi: AuthenticationService = AuthenticationProvider().provideAuthentication()
}