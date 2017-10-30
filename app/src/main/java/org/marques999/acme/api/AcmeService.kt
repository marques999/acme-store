package org.marques999.acme.api

import com.squareup.moshi.Types
import io.reactivex.Observable
import org.marques999.acme.model.*

class AcmeService(
    private val apiService: AcmeApi,
    private val username: String,
    private val crypto: CryptographyService
) {

    /**
     */
    fun getCustomer(): Observable<Customer> {
        return apiService.getCustomer(username)
    }

    /**
     */
    fun deleteCustomer(): Observable<Any> {
        return apiService.deleteCustomer(username)
    }

    /**
     */
    fun updateCustomer(customer: CustomerPOST): Observable<Customer> {
        return apiService.updateCustomer(username, customer)
    }

    /**
     */
    fun getProducts(): Observable<List<Product>> {
        return apiService.getProducts()
    }

    /**
     */
    fun getProduct(barcode: String): Observable<Product> {
        return apiService.getProduct(barcode)
    }

    /**
     */
    fun getOrders(): Observable<List<OrderJSON>> {
        return apiService.getOrders()
    }

    /**
     */
    fun getOrder(token: String): Observable<OrderJSON> {
        return apiService.getOrder(token)
    }

    /**
     */
    fun insertOrder(products: List<OrderProductPOST>): Observable<Order> {

        val jsonPayload = AcmeFactory.getMoshi().adapter<List<OrderProductPOST>>(
            Types.newParameterizedType(List::class.java, OrderProductPOST::class.java)
        ).toJson(products)

        return apiService.insertOrder(
            OrderPOST(products, crypto.signPayload(jsonPayload))
        )
    }

    /**
     */
    fun deleteOrder(token: String): Observable<Any> {
        return apiService.deleteOrder(token)
    }
}