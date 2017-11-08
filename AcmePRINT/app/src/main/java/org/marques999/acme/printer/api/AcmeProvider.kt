package org.marques999.acme.printer.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import org.marques999.acme.printer.AcmePrinter
import org.marques999.acme.printer.common.Session

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AcmeProvider(session: Session) {

    /**
     */
    private val interceptor = Interceptor {
        it.proceed(it.request().newBuilder().addHeader(
            "Authorization", session.wrapToken()
        ).build())
    }

    /**
     */
    private val api = Retrofit.Builder().client(
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    ).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(AcmePrinter.jsonSerializer)
    ).baseUrl(
        AcmePrinter.SERVER_URL
    ).build().create(AcmeApi::class.java)

    /**
     */
    fun getOrder(token: String) = api.getOrder(token)
}