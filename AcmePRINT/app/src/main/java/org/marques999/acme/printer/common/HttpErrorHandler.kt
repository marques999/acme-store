package org.marques999.acme.printer.common

import android.content.Context
import io.reactivex.functions.Consumer
import org.marques999.acme.printer.AcmePrinter

class HttpErrorHandler(private val context: Context) : Consumer<Throwable> {

    /**
     */
    private val serializer = AcmePrinter.jsonSerializer.adapter(Response::class.java)

    /**
     */
    override fun accept(throwable: Throwable) {

        if (throwable is retrofit2.HttpException) {
            serializer.fromJson((throwable.response().errorBody()!!).source())?.let {
                AcmeDialogs.buildOk(context, throwable.javaClass.name, it.error!!).show()
            }
        } else {
            AcmeDialogs.buildOk(context, throwable.javaClass.name, throwable.localizedMessage).show()
        }
    }
}