package org.marques999.acme.store.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

import io.reactivex.Observable

import org.marques999.acme.store.model.Order
import org.marques999.acme.store.model.OrderJSON
import org.marques999.acme.store.model.OrderPOST
import org.marques999.acme.store.model.Product

interface AcmeApi {

    /**
     * Products
     */
    @GET("products/")
    fun getProducts(): Observable<List<Product>>

    @GET("products/{id}")
    fun getProduct(@Path("id") barcode: String): Observable<Product>

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