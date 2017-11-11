package org.marques999.acme.store.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import io.reactivex.Observable

import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.model.Authentication
import org.marques999.acme.store.model.Customer
import org.marques999.acme.store.model.CustomerPOST

class AuthenticationProvider {

    /**
     */
    private val api = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(AcmeStore.jsonSerializer)
    ).baseUrl(
        AcmeStore.SERVER_URL
    ).build().create(AuthenticationApi::class.java)

    /**
     */
    fun login(authentication: Authentication) = api.login(authentication)

    /**
     */
    fun register(customer: CustomerPOST): Observable<Customer> = api.register(customer)
}