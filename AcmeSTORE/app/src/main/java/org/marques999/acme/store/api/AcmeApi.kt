package org.marques999.acme.store.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

import io.reactivex.Observable
import org.marques999.acme.store.model.*

interface AcmeApi {

    /**
     * Products
     */
    @GET("products/")
    fun getProducts(): Observable<List<Product>>

    @GET("products/{id}")
    fun getProduct(@Path("id") barcode: String): Observable<Product>

    @GET("customers/{id}")
    fun getCustomer(@Path("id") username: String): Observable<CustomerJSON>

    /**
     * Orders
     */
    @GET("orders/")
    fun getOrders(): Observable<List<Order>>

    @GET("orders/{id}")
    fun getOrder(@Path("id") token: String): Observable<OrderJSON>

    @POST("orders/")
    fun insertOrder(@Body order: OrderPOST): Observable<Order>
}