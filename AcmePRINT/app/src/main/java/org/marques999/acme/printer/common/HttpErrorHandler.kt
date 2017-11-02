package org.marques999.acme.printer.common

import java.util.Date

import android.content.Context

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import io.reactivex.functions.Consumer

import retrofit2.HttpException

class HttpErrorHandler(private val applicationContext: Context) : Consumer<Throwable> {

    private val serializer = Moshi.Builder().add(
        Date::class.java,
        Rfc3339DateJsonAdapter().nullSafe()
    ).build()!!

    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {
            serializer.adapter(Response::class.java).fromJson((throwable.response()?.errorBody() ?: return).source())?.let {
                AcmeDialogs.showOk(applicationContext, throwable.javaClass.name, it.error!!)
            }
        } else {
            AcmeDialogs.showOk(
                applicationContext, throwable.javaClass.name, throwable.localizedMessage
            )
        }
    }
}