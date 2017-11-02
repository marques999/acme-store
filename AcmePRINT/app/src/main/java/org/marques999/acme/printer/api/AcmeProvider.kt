package org.marques999.acme.printer.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import io.reactivex.Observable

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import org.marques999.acme.printer.orders.Order
import org.marques999.acme.printer.common.Token

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.Date

class AcmeProvider(session: Token) {

    private val interceptor = Interceptor {

        return@Interceptor it.proceed(
            it.request().newBuilder().addHeader(
                "Authorization",
                "Bearer " + session.token
            ).build()
        )
    }

    private val serializer = Moshi.Builder().add(
        Date::class.java,
        Rfc3339DateJsonAdapter().nullSafe()
    ).build()!!

    private val api = Retrofit.Builder().client(
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    ).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(serializer)
    ).baseUrl(
        "http:/192.168.1.93:3333/"
    ).build().create(AcmeApi::class.java)

    fun getOrder(token: String): Observable<Order> {
        return api.getOrder(token)
    }
}