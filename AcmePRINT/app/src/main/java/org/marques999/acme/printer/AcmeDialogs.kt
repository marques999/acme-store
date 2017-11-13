package org.marques999.acme.printer

import android.app.AlertDialog
import android.content.Context

object AcmeDialogs {

    fun buildOk(
        context: Context,
        resourceId: Int,
        vararg format: Any
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(R.string.activity_main)
        .setMessage(context.getString(resourceId, *format))
        .setPositiveButton(android.R.string.ok, null)
}