package org.marques999.acme.store.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import com.squareup.moshi.Types

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import org.marques999.acme.store.customers.Session

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.Date

import org.marques999.acme.store.customers.CustomerPOST
import org.marques999.acme.store.orders.OrderPOST
import org.marques999.acme.store.orders.OrderProductPOST

class AcmeProvider(private val session: Session, private val crypto: CryptographyProvider) {

    /**
     */
    private val interceptor = Interceptor {
        it.proceed(
            it.request().newBuilder().addHeader(
                "Authorization", "Bearer " + session.token
            ).build()
        )
    }

    /**
     */
    private val serializer = Moshi.Builder().add(
        Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
    ).build()

    /**
     */
    private val api = Retrofit.Builder().client(
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    ).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(serializer)
    ).baseUrl(
        "http:/192.168.1.93:3333/"
    ).build().create(AcmeApi::class.java)

    /**
     */
    private val listAdapter = serializer.adapter<List<OrderProductPOST>>(
        Types.newParameterizedType(List::class.java, OrderProductPOST::class.java)
    )

    /**
     */
    fun getOrders() = api.getOrders()
    fun getProducts() = api.getProducts()
    fun getOrder(token: String) = api.getOrder(token)
    fun getProduct(barcode: String) = api.getProduct(barcode)

    /**
     */
    fun deleteOrder(token: String) = api.deleteOrder(token)
    fun deleteCustomer() = api.deleteCustomer(session.customer.username)

    /**
     */
    fun updateCustomer(customer: CustomerPOST) = api.updateCustomer(
        session.customer.username, customer
    )

    /**
     */
    fun insertOrder(products: List<OrderProductPOST>) = api.insertOrder(
        OrderPOST(products, crypto.signPayload(listAdapter.toJson(products)))
    )
}