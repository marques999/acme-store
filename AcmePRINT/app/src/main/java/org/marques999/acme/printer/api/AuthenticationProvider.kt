package org.marques999.acme.printer.api

import org.marques999.acme.printer.common.Authentication
import org.marques999.acme.printer.common.SessionJwt

import io.reactivex.Observable

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import org.marques999.acme.printer.AcmePrinter

class AuthenticationProvider {

    /**
     */
    private val api = Retrofit.Builder().addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(AcmePrinter.jsonSerializer)
    ).baseUrl(
        AcmePrinter.SERVER_URL
    ).build().create(AuthenticationApi::class.java)

    /**
     */
    fun login(username: String, password: String): Observable<SessionJwt> {
        return api.login(Authentication(username, password))
    }
}