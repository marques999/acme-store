package org.marques999.acme.store.views.register

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v4.app.Fragment

import org.marques999.acme.store.R

object RegisterConstants {

    /**
     */
    private val NIF_LENGTH = 9
    private val USERNAME_MINIMUM = 4
    private val PASSWORD_MINIMUM = 6

    /**
     */
    internal val EXTRA_USERNAME = "org.marques999.acme.store.USERNAME"
    internal val EXTRA_PASSWORD = "org.marques999.acme.store.PASSWORD"

    /**
     */
    private val messageParams = mapOf(
        R.string.errorNif to NIF_LENGTH,
        R.string.errorPassword to PASSWORD_MINIMUM,
        R.string.errorUsername to USERNAME_MINIMUM
    )

    /**
     */
    fun Activity.generateError(@StringRes resource: Int): String = messageParams[resource].let {

        if (it != null) {
            getString(resource, it)
        } else {
            getString(resource)
        }
    }

    /**
     */
    fun Fragment.generateError(@StringRes resource: Int): String = messageParams[resource].let {

        if (it != null) {
            getString(resource, it)
        } else {
            getString(resource)
        }
    }

    /**
     */
    fun invalidNif(taxNumber: String) = taxNumber.length < NIF_LENGTH
    fun invalidUsername(username: String) = username.length < USERNAME_MINIMUM
    fun invalidPassword(password: String) = password.length < PASSWORD_MINIMUM
}