package org.marques999.acme.store.views.register

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v4.app.Fragment

import org.marques999.acme.store.R

object RegisterConstants {

    /**
     */
    private val NIF_LENGTH = 9
    private val CARD_MINIMUM = 12
    private val USERNAME_MINIMUM = 4
    private val PASSWORD_MINIMUM = 6

    /**
     */
    internal val EXTRA_USERNAME = "org.marques999.acme.store.extra.USERNAME"
    internal val EXTRA_PASSWORD = "org.marques999.acme.store.extra.PASSWORD"

    /**
     */
    private val messageParams = mapOf(
        R.string.error_nif to NIF_LENGTH,
        R.string.error_card to CARD_MINIMUM,
        R.string.error_password to PASSWORD_MINIMUM,
        R.string.error_username to USERNAME_MINIMUM
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
    fun invalidCard(cardNumber: String) = cardNumber.length < CARD_MINIMUM
    fun invalidUsername(username: String) = username.length < USERNAME_MINIMUM
    fun invalidPassword(password: String) = password.length < PASSWORD_MINIMUM
}