package org.marques999.acme.store

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog

import android.content.Context
import android.content.DialogInterface

object AcmeDialogs {

    /**
     */
    fun buildOk(
        context: Context,
        throwable: Exception,
        message: String
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(throwable.javaClass.simpleName)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)

    /**
     */
    fun buildOk(
        context: Context,
        resourceId: Int,
        vararg params: Any
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(R.string.activity_main)
        .setMessage(context.getString(resourceId, params))
        .setPositiveButton(android.R.string.ok, null)

    /**
     */
    fun buildProgress(context: Context, messageId: Int) = ProgressDialog(
        context
    ).apply {
        isIndeterminate = true
        setMessage(context.getString(messageId))
    }

    /**
     */
    fun buildYesNo(
        context: Activity,
        resourceId: Int,
        callback: DialogInterface.OnClickListener
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(R.string.activity_main)
        .setMessage(resourceId)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes, callback)
}