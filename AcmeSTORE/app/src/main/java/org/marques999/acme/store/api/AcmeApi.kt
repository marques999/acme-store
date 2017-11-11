package org.marques999.acme.store.api

import retrofit2.http.*

import io.reactivex.Observable
import org.marques999.acme.store.model.*

interface AcmeApi {

    /**
     * Customers
     */
    @DELETE("customers/{id}")
    fun deleteCustomer(@Path("id") username: String): Observable<Any>

    @PUT("customers/{id}")
    fun updateCustomer(@Path("id") username: String, @Body customer: CustomerPOST): Observable<Customer>

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

    @DELETE("orders/{id}")
    fun deleteOrder(@Path("id") token: String): Observable<Any>
}