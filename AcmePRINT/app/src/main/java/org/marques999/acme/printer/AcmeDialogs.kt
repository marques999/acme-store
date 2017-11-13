package org.marques999.acme.printer

import android.app.Activity
import android.app.AlertDialog

import android.content.Context
import android.content.DialogInterface

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

    fun buildYesNo(
        context: Activity,
        resourceId: Int,
        callback: DialogInterface.OnClickListener
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(R.string.activity_main)
        .setMessage(resourceId)
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes, callback)
}