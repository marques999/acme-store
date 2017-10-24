package org.marques999.acme.api

import io.reactivex.Observable

import org.marques999.acme.model.Authentication
import org.marques999.acme.model.Token

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("login")
    fun login(@Body payload: Authentication): Observable<Token>
}
