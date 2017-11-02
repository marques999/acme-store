package org.marques999.acme.printer.common

import android.app.Activity
import android.app.AlertDialog

import android.content.Context
import android.content.DialogInterface

object AcmeDialogs {

    fun showOk(
        context: Context,
        title: String, message: String
    ) = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null).show()!!

    fun showYesNo(
        context: Activity,
        title: CharSequence,
        message: CharSequence,
        callback: DialogInterface.OnClickListener
    ) = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes, callback).show()!!
}