package org.marques999.acme.api

import io.reactivex.Observable
import org.marques999.acme.model.*
import retrofit2.http.*

interface AcmeApi {

    /**
     */
    @GET("customers/{id}")
    fun getCustomer(@Path("id") username: String): Observable<Customer>

    /**
     */
    @DELETE("customers/{id}")
    fun deleteCustomer(@Path("id") username: String): Observable<Any>

    /**
     */
    @PUT("customers/{id}")
    fun updateCustomer(@Path("id") username: String, @Body customer: CustomerPOST): Observable<Customer>

    /**
     */
    @GET("products/")
    fun getProducts(): Observable<List<Product>>

    /**
     */
    @GET("products/{id}")
    fun getProduct(@Path("id") barcode: String): Observable<Product>

    /**
     */
    @GET("orders/")
    fun getOrders(): Observable<List<OrderJSON>>

    /**
     */
    @POST("orders/")
    fun insertOrder(@Body order: OrderPOST): Observable<Order>

    /**
     */
    @GET("orders/{id}")
    fun getOrder(@Path("id") token: String): Observable<OrderJSON>

    /**
     */
    @DELETE("orders/{id}")
    fun deleteOrder(@Path("id") token: String): Observable<Any>
}