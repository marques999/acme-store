package org.marques999.acme.printer.api

import io.reactivex.Observable

import org.marques999.acme.printer.orders.Order

import retrofit2.http.GET
import retrofit2.http.Path

interface AcmeApi {

    @GET("orders/{id}")
    fun getOrder(@Path("id") token: String): Observable<Order>
}