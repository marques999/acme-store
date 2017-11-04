package org.marques999.acme.store.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.Date

import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.customers.CreditCard
import org.marques999.acme.store.customers.Customer
import org.marques999.acme.store.customers.CustomerPOST

import io.reactivex.Observable

class AuthenticationProvider {

    /**
     */
    private val serializer: Moshi = Moshi.Builder().add(
        Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
    ).build()

    /**
     */
    private val api = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(serializer)
    ).baseUrl(
        AcmeStore.SERVER_URL
    ).build().create(AuthenticationApi::class.java)

    /**
     */
    fun register(
        name: String,
        username: String,
        password: String,
        address1: String,
        address2: String,
        country: String,
        tax_number: String,
        public_key: String,
        credit_card: CreditCard
    ): Observable<Customer> = api.register(
        CustomerPOST(
            name,
            username,
            password,
            address1,
            address2,
            country,
            tax_number,
            public_key,
            credit_card
        )
    )

    /**
     */
    fun login(authentication: Authentication) = api.login(authentication)
}