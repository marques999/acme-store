package org.marques999.acme.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.Date

object AcmeFactory {

    /**
     */
    private fun generateHttpClient(token: String) = OkHttpClient.Builder().addInterceptor(
        {
            return@addInterceptor it.proceed(
                it.request().newBuilder().addHeader("Authorization", "Bearer " + token).build()
            )
        }
    ).build()

    /**
     */
    fun getMoshi() = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()!!

    /**
     */
    fun createAuthorized(token: String): AcmeApi = Retrofit.Builder()
        .client(generateHttpClient(token))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
        .baseUrl("http://10.0.2.2:3333/")
        .build().create(AcmeApi::class.java)

    /**
     */
    fun createUnauthorized(): AuthenticationApi = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
        .baseUrl("http://10.0.2.2:3333/")
        .build().create(AuthenticationApi::class.java)
}