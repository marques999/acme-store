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
    private fun decodePem(privateKey: String): PrivateKey {

        val keyContent = privateKey.replace(
            "\\n", ""
        ).replaceFirst(
            AcmeStore.CERTIFICATE_BEGIN, ""
        ).replaceFirst(
            AcmeStore.CERTIFICATE_END, ""
        )

        return KeyFactory.getInstance(AcmeStore.ALGORITHM_PKCS).generatePrivate(
            PKCS8EncodedKeySpec(Base64.decode(keyContent, Base64.DEFAULT))
        ) as RSAPrivateKey
    }

    /**
     */
    fun signPayload(payload: String): String = Base64.encodeToString(
        signature.apply { update(payload.toByteArray()) }.sign(), Base64.DEFAULT
    )
}