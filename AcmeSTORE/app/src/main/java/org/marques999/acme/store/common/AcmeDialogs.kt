package org.marques999.acme.store.common

import android.app.AlertDialog
import android.content.Context

object AcmeDialogs {

    fun showOk(
        context: Context,
        title: String,
        message: String
    ): AlertDialog = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null).show()
}