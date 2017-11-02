package org.marques999.acme.api

import android.util.Base64

import java.security.KeyFactory
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec

class CryptographyProvider(private val privatePem: String) {

    fun provideCryptography(): CryptographyService {

        val keyContent = privatePem.replace(
            "\\n",
            ""
        ).replaceFirst(
            "-----BEGIN PRIVATE KEY-----",
            ""
        ).replaceFirst(
            "-----END PRIVATE KEY-----",
            ""
        )

        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(
            PKCS8EncodedKeySpec(Base64.decode(keyContent, Base64.DEFAULT))
        ) as RSAPrivateKey

        return CryptographyService(privateKey, Signature.getInstance("SHA1WithRSA"))
    }
}