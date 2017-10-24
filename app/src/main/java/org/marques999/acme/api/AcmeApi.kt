package org.marques999.acme.api

import io.reactivex.Observable
import org.marques999.acme.model.*

import retrofit2.http.*

interface AcmeApi {

    @GET("customers/{id}")
    fun getCustomer(@Path("id") customerId: String): Observable<Customer>

    @POST("customers/")
    fun insertCustoemr(@Body customer: CustomerPOST): Observable<Customer>

    @PUT("customers/{id}")
    fun updateCustomer(@Path("id") customerId: String, @Body customer: CustomerPOST):
        Observable<Customer>

    @GET("products/")
    fun getProducts(): Observable<List<Product>>

    @GET("products/{id}")
    fun getProduct(@Path("id") productId: String): Observable<Product>

    @GET("orders/")
    fun getOrders(): Observable<List<Transaction>>

    @GET("orders/{id}")
    fun getOrder(@Path("id") orderId: Int): Observable<Transaction>

    @POST("orders/")
    fun insertOrder(@Body order: Order): Observable<Order>

    @DELETE("orders/{id}")
    fun deleteOrder(@Path("id") orderId: Int): Observable<Any>
}