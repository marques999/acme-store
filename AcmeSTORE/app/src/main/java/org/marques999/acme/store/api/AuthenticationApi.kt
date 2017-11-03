package org.marques999.acme.store.api

import org.marques999.acme.store.common.Authentication

import retrofit2.http.Body
import retrofit2.http.POST

import io.reactivex.Observable

import org.marques999.acme.store.customers.Customer
import org.marques999.acme.store.customers.CustomerPOST
import org.marques999.acme.store.customers.Jwt

interface AuthenticationApi {

    @POST("login")
    fun login(@Body payload: Authentication): Observable<Jwt>

    @POST("customers")
    fun register(@Body customerPOST: CustomerPOST): Observable<Customer>
}