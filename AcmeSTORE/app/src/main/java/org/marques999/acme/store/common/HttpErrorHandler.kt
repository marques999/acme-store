package org.marques999.acme.store.common

import java.util.Date

import android.content.Context

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import io.reactivex.functions.Consumer

import retrofit2.HttpException

class HttpErrorHandler(private val context: Context) : Consumer<Throwable> {

    private val serializer = Moshi.Builder().add(
        Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
    ).build()

    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {
            serializer.adapter(Response::class.java).fromJson((throwable.response().errorBody()!!).source())?.let {
                AcmeDialogs.buildOk(context, throwable.javaClass.name, it.error!!).show()
            }
        } else {
            AcmeDialogs.buildOk(context, throwable.javaClass.name, throwable.localizedMessage).show()
        }
    }
}