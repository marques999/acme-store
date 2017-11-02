package org.marques999.acme.api

import android.util.Base64

import java.security.PrivateKey
import java.security.Signature

class CryptographyService(privateKey: PrivateKey, private val signature: Signature) {

    init {
        signature.initSign(privateKey)
    }

    fun signPayload(payload: String): String {
        signature.update(payload.toByteArray())
        return Base64.encodeToString(signature.sign(), Base64.DEFAULT)
    }
}