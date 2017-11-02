package org.marques999.acme.printer.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import io.reactivex.Observable

import org.marques999.acme.printer.common.Authentication
import org.marques999.acme.printer.common.Token

import java.util.Date

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AuthenticationProvider {

    private val serializer = Moshi.Builder().add(
        Date::class.java,
        Rfc3339DateJsonAdapter().nullSafe()
    ).build()!!

    private val api = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(serializer)
    ).baseUrl(
        "http://192.168.1.93:3333/"
    ).build().create(AuthenticationApi::class.java)

    fun login(username: String, password: String): Observable<Token> {
        return api.login(Authentication(username, password))
    }
}