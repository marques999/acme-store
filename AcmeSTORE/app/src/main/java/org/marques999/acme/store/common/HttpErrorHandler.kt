package org.marques999.acme.store.common

import android.content.Context
import io.reactivex.functions.Consumer
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.model.Response
import retrofit2.HttpException

class HttpErrorHandler(private val context: Context) : Consumer<Throwable> {

    /**
     */
    private val serializer = AcmeStore.jsonSerializer.adapter(Response::class.java)

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