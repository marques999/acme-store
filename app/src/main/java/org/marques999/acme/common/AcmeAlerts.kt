package org.marques999.acme.common

import android.app.AlertDialog
import android.content.Context

class AcmeAlerts(private val context: Context) {

    fun showOk(title: String, message: String) = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null).show()!!
}