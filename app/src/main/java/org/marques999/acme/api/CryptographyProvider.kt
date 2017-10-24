package org.marques999.acme.api

import android.util.Base64
import android.util.Log

import java.security.MessageDigest
import java.security.PrivateKey
import java.security.Signature

import javax.crypto.Cipher

class CryptographyProvider(private val privateKey: PrivateKey) {

    private val signature = Signature.getInstance("SHA1WithRSA")

    init {
        Log.d("acme.service", "Private: (" + privateKey.toString() + ")\n")
        signature.initSign(privateKey)
        Log.d("acme.service", "Signature: (" + signature.sign().size + "bytes)\n")
    }

    fun base64Encode(payload: String): String = Base64.encodeToString(
        payload.toByteArray(charset("UTF-8")),
        Base64.DEFAULT
    ).trim()

    fun base64Decode(payload: String): String = String(
        Base64.decode(payload, Base64.DEFAULT), charset("UTF-8")
    )

    private fun sha1Encode(sha1Message: ByteArray): String = Base64.encodeToString(
        MessageDigest.getInstance("SHA-1").digest(sha1Message),
        Base64.DEFAULT
    ).trim()

    fun signPayload(payload: String): String {
        signature.update(payload.toByteArray())
        return Base64.encodeToString(signature.sign(), Base64.DEFAULT)
    }

    private fun rsaDecrypt(rsaPayload: String, sha1Digest: String): String {

        try {

            val rsaCipher = Cipher.getInstance("RSA/None/PKCS1Padding")

            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)

            val jsonData = rsaCipher.doFinal(Base64.decode(rsaPayload, Base64.DEFAULT))

            return if (sha1Encode(jsonData) == sha1Digest) {
                String(jsonData, charset("UTF-8"))
            } else {
                ""
            }
        } catch (ex: Exception) {
            return ""
        }
    }
}