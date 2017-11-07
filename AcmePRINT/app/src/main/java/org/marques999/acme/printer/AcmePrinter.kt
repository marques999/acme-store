package org.marques999.acme.printer

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import java.util.Date
import java.util.Locale
import java.util.Currency

import org.marques999.acme.printer.api.AcmeProvider
import org.marques999.acme.printer.api.AuthenticationProvider
import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.common.Session
import org.marques999.acme.printer.common.SessionJwt

import java.text.DateFormat
import java.text.NumberFormat

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
        username: String,
        password: String,
        callback: Consumer<SessionJwt>
    ): Boolean {

        if (acmeApi != null) {
            return true
        } else {
            AuthenticationProvider().login(
                username, password
            ).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeOn(
                Schedulers.io()
            ).subscribe(
                authenticationHandler(username, callback),
                HttpErrorHandler(this)
            )
        }

        return false
    }

    /**
     */
    companion object {

        private val ZXING_PACKAGE = "com.google.zxing.client.android"

        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val SERVER_URL = "http://192.168.1.93:3333/"
        val RAMEN_RECIPE = "mieic@feup#2017".toByteArray()
        val ZXING_URL = "market://details?id=$ZXING_PACKAGE"
        val EXTRA_TOKEN = "org.marques999.acme.printer.TOKEN"
        val BUNDLE_ORDER = "org.marques999.acme.printer.ORDER"

        val jsonSerializer: Moshi by lazy {
            Moshi.Builder().add(
                Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
            ).build()
        }

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