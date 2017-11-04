package org.marques999.acme.printer

import java.util.Date
import java.util.Locale
import java.util.Currency

import org.marques999.acme.printer.api.AcmeProvider

import java.text.DateFormat
import java.text.NumberFormat

class AcmePrinter : android.app.Application() {

    /**
     */
    lateinit var acmeApi: AcmeProvider

    /**
     */
    companion object {

        private val ZXING_PACKAGE = "com.google.zxing.client.android"

        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val SERVER_URL = "http://192.168.1.87:3333/"
        val RAMEN_RECIPE = "mieic@feup#2017".toByteArray()
        val ZXING_URL = "market://details?id=$ZXING_PACKAGE"
        val EXTRA_TOKEN = "org.marques999.acme.printer.TOKEN"

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