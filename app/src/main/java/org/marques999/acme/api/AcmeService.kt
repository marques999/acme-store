package org.marques999.acme.api

import com.squareup.moshi.Types

import io.reactivex.Observable

import org.marques999.acme.model.*

class AcmeService(private val apiService: AcmeApi, private val crypto: CryptographyProvider) {

    fun insertCustoemr(
        name: String,
        username: String,
        email: String,
        address: String,
        password: String,
        nif: String,
        country: String,
        credit_card: CreditCard
    ): Observable<Customer> = apiService.insertCustoemr(
        CustomerPOST(name, email, username, password, address, nif, country, credit_card)
    )

    fun getCustomer(customerId: String): Observable<Customer> {
        return apiService.getCustomer(customerId)
    }

    fun updateCustomer(customerId: String, customer: CustomerPOST): Observable<Customer> {
        return apiService.updateCustomer(customerId, customer)
    }

    fun getProducts(): Observable<List<Product>> {
        return apiService.getProducts()
    }

    fun getProduct(productId: String): Observable<Product> {
        return apiService.getProduct(productId)
    }

    fun getOrders(): Observable<List<Transaction>> {
        return apiService.getOrders()
    }

    fun getOrder(orderId: Int): Observable<Transaction> {
        return apiService.getOrder(orderId)
    }

    private fun productsToJson(products: List<String>): String {

        return AcmeFactory.getMoshi().adapter<List<String>>(
            Types.newParameterizedType(List::class.java, String::class.java)
        ).toJson(products)
    }

    fun insertOrder(products: List<String>): Observable<Order> {

        val jsonPayload = productsToJson(products)

        return apiService.insertOrder(
            Order(crypto.base64Encode(jsonPayload), crypto.signPayload(jsonPayload))
        )
    }

    fun deleteOrder(orderId: Int): Observable<Any> {
        return apiService.deleteOrder(orderId)
    }
}