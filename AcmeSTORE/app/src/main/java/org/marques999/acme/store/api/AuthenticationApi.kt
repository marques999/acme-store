package org.marques999.acme.store.api

import retrofit2.http.Body
import retrofit2.http.POST

import io.reactivex.Observable

import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.customers.Customer
import org.marques999.acme.store.customers.CustomerPOST
import org.marques999.acme.store.common.SessionJwt

interface AuthenticationApi {

    @POST("login")
    fun login(@Body payload: Authentication): Observable<SessionJwt>

    @POST("customers/")
    fun register(@Body customerPOST: CustomerPOST): Observable<Customer>
}