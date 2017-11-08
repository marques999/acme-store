package org.marques999.acme.printer.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import io.reactivex.Observable

import org.marques999.acme.printer.AcmePrinter
import org.marques999.acme.printer.common.Authentication
import org.marques999.acme.printer.common.SessionJwt

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