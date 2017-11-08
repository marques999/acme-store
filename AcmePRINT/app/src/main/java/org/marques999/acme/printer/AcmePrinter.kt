package org.marques999.acme.printer

import android.content.Context

import java.text.DateFormat
import java.text.NumberFormat

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import java.util.Date
import java.util.Locale
import java.util.Currency

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.printer.api.AcmeProvider
import org.marques999.acme.printer.api.AuthenticationProvider
import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.common.Session
import org.marques999.acme.printer.common.SessionJwt

class AcmePrinter : android.app.Application() {

    /**
     */
    var acmeApi: AcmeProvider? = null

    /**
     */
    private fun authenticationHandler(
        username: String,
        next: Consumer<SessionJwt>
    ) = Consumer<SessionJwt> {
        acmeApi = AcmeProvider(Session(it, username))
        next.accept(it)
    }

    /**
     */
    fun authenticate(
        context: Context,
        callback: Consumer<SessionJwt>
    ): Boolean {

        if (acmeApi != null) {
            return true
        }

        AuthenticationProvider().login(
            "admin", "admin"
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            authenticationHandler("admin", callback),
            HttpErrorHandler(context)
        )

        return false
    }

    /**
     */
    companion object {

        private val ZXING_PACKAGE = "com.google.zxing.client.android"

        val SERVER_URL = "http://10.0.2.2:3333/"
        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val RAMEN_RECIPE = "mieic@feup#2017".toByteArray()
        val ZXING_URL = "market://details?id=$ZXING_PACKAGE"

        val jsonSerializer: Moshi = Moshi.Builder().add(
            Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
        ).build()

        fun formatDate(dateTime: Date): String = DateFormat.getDateInstance(
            DateFormat.MEDIUM, Locale("pt", "PT")
        ).format(dateTime)

        fun formatDateTime(dateTime: Date): String = DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM, DateFormat.MEDIUM, Locale("pt", "PT")
        ).format(dateTime)

        fun formatCurrency(value: Double): String = NumberFormat.getCurrencyInstance(
            Locale.getDefault()
        ).apply {
            currency = Currency.getInstance("EUR")
        }.format(value)
    }
}