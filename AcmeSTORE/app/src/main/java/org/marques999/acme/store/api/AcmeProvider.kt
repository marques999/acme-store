package org.marques999.acme.store.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import com.squareup.moshi.Types

import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.model.CustomerJSON
import org.marques999.acme.store.model.Session
import org.marques999.acme.store.model.OrderPOST
import org.marques999.acme.store.model.OrderProductPOST

import io.reactivex.Observable

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AcmeProvider(private val session: Session, private val crypto: CryptographyProvider) {

    /**
     */
    private val interceptor = Interceptor {
        it.proceed(it.request().newBuilder().addHeader(
            "Authorization", session.wrapToken()
        ).build())
    }

    /**
     */
    private val api = Retrofit.Builder().client(
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    ).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()
    ).addConverterFactory(
        MoshiConverterFactory.create(AcmeStore.jsonSerializer)
    ).baseUrl(
        AcmeStore.SERVER_URL
    ).build().create(AcmeApi::class.java)

    /**
     */
    private val listAdapter = AcmeStore.jsonSerializer.adapter<List<OrderProductPOST>>(
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
    fun getCustomer(refreshCustomer: Boolean): Observable<CustomerJSON> = if (refreshCustomer) {
        api.getCustomer(session.username)
    } else {
        Observable.just(session.customer)
    }

    /**
     */
    fun insertOrder(products: List<OrderProductPOST>) = api.insertOrder(
        OrderPOST(products, crypto.signPayload(listAdapter.toJson(products)))
    )
}