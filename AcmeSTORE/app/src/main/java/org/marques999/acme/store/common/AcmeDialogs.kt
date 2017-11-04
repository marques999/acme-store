package org.marques999.acme.store.common

import android.app.AlertDialog
import android.content.Context

import org.marques999.acme.store.R

object AcmeDialogs {

    /**
     */
    fun buildOk(
        context: Context,
        resourceId: Int,
        vararg format: Any
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(R.string.app_name)
        .setMessage(context.getString(resourceId, *format))
        .setPositiveButton(android.R.string.ok, null)

    /**
     */
    fun buildOk(
        context: Context,
        title: String,
        message: String
    ): AlertDialog.Builder = AlertDialog
        .Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
}