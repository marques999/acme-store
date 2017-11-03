package org.marques999.acme.store.api

import android.util.Base64

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec

class CryptographyProvider(privatePem: String) {

    /**
     */
    private val signature = Signature.getInstance("SHA1WithRSA").apply {
        initSign(decodePem(privatePem))
    }

    /**
     */
    private fun decodePem(privateKey: String): PrivateKey {

        val keyContent = privateKey.replace(
            "\\n",
            ""
        ).replaceFirst(
            "-----BEGIN PRIVATE KEY-----",
            ""
        ).replaceFirst(
            "-----END PRIVATE KEY-----",
            ""
        )

        return KeyFactory.getInstance("RSA").generatePrivate(
            PKCS8EncodedKeySpec(Base64.decode(keyContent, Base64.DEFAULT))
        ) as RSAPrivateKey
    }

    /**
     */
    fun signPayload(payload: String): String = Base64.encodeToString(
        signature.apply { update(payload.toByteArray()) }.sign(), Base64.DEFAULT
    )
}