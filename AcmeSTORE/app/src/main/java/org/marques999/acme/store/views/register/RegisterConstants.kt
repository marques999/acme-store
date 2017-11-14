package org.marques999.acme.store.views.register

import org.marques999.acme.store.R
import android.support.v4.app.Fragment

object RegisterConstants {

    /**
     */
    private val NIF_LENGTH = 9
    private val CARD_MINIMUM = 12
    private val USERNAME_MINIMUM = 4
    private val PASSWORD_MINIMUM = 6

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
    fun Fragment.generateError(resourceId: Int): String = messageParams[resourceId].let {

        if (it != null) {
            getString(resourceId, it)
        } else {
            getString(resourceId)
        }
    }

    /**
     */
    fun invalidNif(taxNumber: String) = taxNumber.length < NIF_LENGTH
    fun invalidCard(cardNumber: String) = cardNumber.length < CARD_MINIMUM
    fun invalidUsername(username: String) = username.length < USERNAME_MINIMUM
    fun invalidPassword(password: String) = password.length < PASSWORD_MINIMUM
}