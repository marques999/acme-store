package org.marques999.acme.printer.api

import android.app.AlertDialog
import android.content.Context
import retrofit2.HttpException
import io.reactivex.functions.Consumer

import org.marques999.acme.printer.AcmePrinter
import org.marques999.acme.printer.model.Response

class HttpErrorHandler(private val context: Context) : Consumer<Throwable> {

    private fun buildOk(
        context: Context,
        throwable: Exception,
        message: String
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(throwable.javaClass.simpleName)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)

    private val serializer = AcmePrinter.jsonSerializer.adapter(
        Response::class.java
    )

    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {
            serializer.fromJson((throwable.response().errorBody()!!).source())?.let {
                buildOk(context, throwable, it.error!!).show()
            }
        } else if (throwable is Exception) {
            buildOk(context, throwable, throwable.localizedMessage).show()
        }
    }
}