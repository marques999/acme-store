package org.marques999.acme.store.common

import android.app.AlertDialog
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import io.reactivex.functions.Consumer

import retrofit2.HttpException
import java.util.*

class HttpErrorHandler(private val applicationContext: Context) : Consumer<Throwable> {

    private val serializer = Moshi.Builder().add(
        Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
    ).build()

    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {

            val errorBody = throwable.response()?.errorBody() ?: return

            serializer.adapter(Response::class.java).fromJson(errorBody.source())?.let {

                AlertDialog.Builder(applicationContext)
                    .setTitle(throwable.javaClass.name)
                    .setMessage(it.error)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        } else {

            AlertDialog.Builder(applicationContext)
                .setTitle(throwable.javaClass.name)
                .setMessage(throwable.message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }
}