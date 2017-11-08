package org.marques999.acme.printer.common

import android.content.Context
import retrofit2.HttpException
import io.reactivex.functions.Consumer
import org.marques999.acme.printer.AcmePrinter

class HttpErrorHandler(private val context: Context) : Consumer<Throwable> {

    /**
     */
    private val serializer = AcmePrinter.jsonSerializer.adapter(Response::class.java)

    /**
     */
    override fun accept(throwable: Throwable) {

        if (throwable is HttpException) {
            serializer.fromJson((throwable.response().errorBody()!!).source())?.let {
                AcmeDialogs.buildOk(context, throwable, it.error!!).show()
            }
        } else if (throwable is Exception) {
            AcmeDialogs.buildOk(context, throwable, throwable.localizedMessage).show()
        }
    }
}