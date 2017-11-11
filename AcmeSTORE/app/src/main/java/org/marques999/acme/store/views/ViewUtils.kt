package org.marques999.acme.store.views

import java.text.DateFormat
import java.text.NumberFormat

import java.util.Date
import java.util.Locale
import java.util.Currency


object ViewUtils {

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