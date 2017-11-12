package org.marques999.acme.printer

import java.text.DateFormat
import java.text.NumberFormat

import java.util.Date
import java.util.Locale
import java.util.Currency

object AcmeUtils {

    /**
     */
    private val applicationLocale = Locale("pt", "PT")

    /**
     */
    private val dateFormat = DateFormat.getDateInstance(
        DateFormat.MEDIUM, applicationLocale
    )

    /**
     */
    private val dateTimeFormat = DateFormat.getDateTimeInstance(
        DateFormat.MEDIUM, DateFormat.MEDIUM, applicationLocale
    )

    /**
     */
    private val currencyFormat = NumberFormat.getCurrencyInstance(
        Locale.getDefault()
    ).apply {
        currency = Currency.getInstance("EUR")
    }

    /**
     */
    fun formatDate(value: Date): String = dateFormat.format(value)
    fun formatDateTime(value: Date): String = dateTimeFormat.format(value)
    fun formatCurrency(value: Double): String = currencyFormat.format(value)
}