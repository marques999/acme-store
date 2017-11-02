package org.marques999.acme.common

import android.app.AlertDialog
import android.content.Context

import io.reactivex.functions.Consumer

import org.marques999.acme.api.AcmeFactory
import org.marques999.acme.model.Response

import retrofit2.HttpException

class HttpErrorHandler(private val applicationContext: Context) : Consumer<Throwable> {

    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {

            val errorBody = throwable.response()?.errorBody() ?: return

            AcmeFactory.getMoshi().adapter(Response::class.java).fromJson(errorBody.source())?.let {

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