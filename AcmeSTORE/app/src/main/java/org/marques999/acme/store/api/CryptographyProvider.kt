package org.marques999.acme.store.api

import android.util.Base64

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec

import org.marques999.acme.store.AcmeStore

class CryptographyProvider(privatePem: String) {

    /**
     */
    private val signature = Signature.getInstance(AcmeStore.ALGORITHM_HASH).apply {
        initSign(decodePem(privatePem))
    }

    /**
     */
    companion object {
        private val CERTIFICATE_BEGIN = "-----BEGIN PRIVATE KEY-----"
        private val CERTIFICATE_END = "-----END PRIVATE KEY-----"
    }

    /**
     */
    private fun decodePem(privateKey: String): PrivateKey {

        val keyContent = privateKey.replace(
            "\\n", ""
        ).replaceFirst(
            CERTIFICATE_BEGIN, ""
        ).replaceFirst(
            CERTIFICATE_END, ""
        )

        return KeyFactory.getInstance(AcmeStore.ALGORITHM_PKCS).generatePrivate(
            PKCS8EncodedKeySpec(Base64.decode(keyContent, Base64.DEFAULT))
        ) as RSAPrivateKey
    }

    /**
     */
    private fun encodeBase64(payload: ByteArray): String {
        return Base64.encodeToString(payload, Base64.DEFAULT)
    }

    /**
     */
    fun signPayload(payload: String): String = encodeBase64(
        signature.apply { update(payload.toByteArray()) }.sign()
    )

    /**
     */
    fun encodePrivate(privateKey: PrivateKey): String {
        return "$CERTIFICATE_BEGIN\n${encodeBase64(privateKey.encoded)}$CERTIFICATE_END\n"
    }
}