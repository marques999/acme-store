package org.marques999.acme.printer

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

import java.util.Date

import org.marques999.acme.printer.model.Session
import org.marques999.acme.printer.model.SessionJwt

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.content.Context

import org.marques999.acme.printer.api.AcmeProvider
import org.marques999.acme.printer.api.AuthenticationProvider
import org.marques999.acme.printer.api.HttpErrorHandler

class AcmePrinter : android.app.Application() {

    lateinit var api: AcmeProvider

    private fun authenticationHandler(
        username: String,
        next: Consumer<SessionJwt>
    ) = Consumer<SessionJwt> {
        authenticated = true
        api = AcmeProvider(Session(it, username))
        next.accept(it)
    }

    private var authenticated = false

    fun authenticate(context: Context, callback: Consumer<SessionJwt>): Boolean {

        if (authenticated) {
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

    companion object {

        val SERVER_URL = "http://192.168.1.87:3333/"
        val ZXING_ACTIVITY = "com.google.zxing.client.android.SCAN"
        val ZXING_URL = "market://details?id=com.google.zxing.client.android"

        val jsonSerializer: Moshi = Moshi.Builder().add(
            Date::class.java, Rfc3339DateJsonAdapter().nullSafe()
        ).build()
    }
}