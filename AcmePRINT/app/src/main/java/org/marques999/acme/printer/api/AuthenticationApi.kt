package org.marques999.acme.printer.api

import retrofit2.http.Body
import retrofit2.http.POST

import io.reactivex.Observable

import org.marques999.acme.printer.model.Authentication
import org.marques999.acme.printer.model.SessionJwt

interface AuthenticationApi {

    @POST("login")
    fun login(@Body payload: Authentication): Observable<SessionJwt>
}