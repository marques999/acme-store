package org.marques999.acme.printer.api

import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Path

import org.marques999.acme.printer.orders.Order

interface AcmeApi {

    @GET("orders/{id}")
    fun getOrder(@Path("id") token: String): Observable<Order>
}