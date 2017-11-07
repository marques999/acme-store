package org.marques999.acme.store.common

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog

import org.marques999.acme.store.R

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.StringRes

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
        .setTitle(R.string.main_activity)
        .setMessage(context.getString(resourceId, params))
        .setPositiveButton(android.R.string.ok, null)

    /**
     */
    fun buildProgress(context: Context, @StringRes messageId: Int) = ProgressDialog(
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
        .setTitle(R.string.main_activity)
        .setMessage(resourceId)
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes, callback)
}