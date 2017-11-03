package org.marques999.acme.store.api

import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.model.Customer
import org.marques999.acme.store.model.CustomerPOST
import org.marques999.acme.store.common.Token

import io.reactivex.Observable

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    /**
     */
    @POST("login")
    fun login(@Body payload: Authentication): Observable<Token>

    /**
     */
    @POST("customers")
    fun register(@Body customerPOST: CustomerPOST): Observable<Customer>
}