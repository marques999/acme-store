package org.marques999.acme.store.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import java.util.Date

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import org.marques999.acme.store.common.Authentication

import org.marques999.acme.store.model.CreditCard
import org.marques999.acme.store.model.Customer
import org.marques999.acme.store.model.CustomerPOST

import io.reactivex.Observable

class AuthenticationProvider {

    /**
     */
    private val serializer = Moshi.Builder().add(
        Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
    ).build()!!

    /**
     */
    private val api = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(serializer)
    ).baseUrl(
        "http://10.0.2.2:3333/"
    ).build().create(AuthenticationApi::class.java)

    /**
     */
    fun register(
        name: String,
        username: String,
        email: String,
        address: String,
        password: String,
        nif: String,
        country: String,
        credit_card: CreditCard
    ): Observable<Customer> = api.register(
        CustomerPOST(name, email, username, password, address, nif, country, credit_card)
    )

    /**
     */
    fun login(username: String, password: String) = api.login(
        Authentication(username, password)
    )
}