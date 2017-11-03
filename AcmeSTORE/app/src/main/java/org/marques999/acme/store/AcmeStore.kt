package org.marques999.acme.store

import android.app.Application

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

import android.content.Context
import android.content.SharedPreferences

import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.CryptographyProvider

import android.util.Base64

class AcmeStore : Application() {

    /**
     */
    lateinit var acmeApi: AcmeProvider
    lateinit var preferences: SharedPreferences
    lateinit var cryptoApi: CryptographyProvider

    /**
     */
    private val defaultKey = """$BEGIN_PRIVATE
MIIBAgIBADANBgkqhkiG9w0BAQEFAASB7TCB6gIBAAIvAL1L9h1N9xqNe0I4ddyjKD6lv0ArcEhB
JbU550urvmvJqa1Rm8Zr+V0+VCp9swcCAwEAAQIuQMQ3rekaDaywqoSU1uu//kdJe1Qhc6a2yGVi
IGzGWDohXDFi/BBaon6D5fJL6QIYAPlTZgR1+jRKpQ9SD687Y4+J+hzwtE+DAhgAwl0wx2zRyIuc
cFySxq3/scDNwAF3Ey0CFybw+7IeqyGXtwgZjRGVeQtmRYZXohH5AhgAg3MJQWaMPqiFJczGC460
BmCSBlA3WwUCGADa450Hyr7r45CqM8xJkERvv3ZuJonBVQ==
$END_PRIVATE"""

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        cryptoApi = CryptographyProvider(preferences.getString(KEY_PRIVATE, defaultKey))
    }

    /**
     */
    fun saveCustomer(
        credentials: Authentication,
        privateKey: PrivateKey
    ) = preferences.edit().apply {
        putString(KEY_USERNAME, credentials.username)
        putString(KEY_PASSWORD, credentials.password)
        putString(KEY_PRIVATE, encodePrivateKey(privateKey))
    }.apply()

    /**
     */
    fun loadCustomer() = Authentication(
        preferences.getString(KEY_USERNAME, "marques999"),
        preferences.getString(KEY_PASSWORD, "r0wsauce")
    )

    /**
     */
    fun generateKeypair(): KeyPair = KeyPairGenerator.getInstance("RSA").apply {
        initialize(368)
    }.generateKeyPair()

    /**
     */
    companion object {

        private val KEY_PREFERENCES = "acmestore"
        private val KEY_PRIVATE = "private"
        private val KEY_USERNAME = "username"
        private val KEY_PASSWORD = "password"
        private val END_PUBLIC = "-----END PUBLIC KEY-----\n"
        private val END_PRIVATE = "-----END PRIVATE KEY-----\n"
        private val BEGIN_PUBLIC = "-----BEGIN PUBLIC KEY-----\n"
        private val BEGIN_PRIVATE = "-----BEGIN PRIVATE KEY-----\n"
    }

    /**
     */
    private fun encodeBase64(payload: ByteArray): String {
        return Base64.encodeToString(payload, Base64.DEFAULT)
    }

    /**
     */
    private fun encodePublicKey(publicKey: PublicKey): String {
        return BEGIN_PUBLIC + encodeBase64(publicKey.encoded) + END_PUBLIC
    }

    /**
     */
    private fun encodePrivateKey(privateKey: PrivateKey): String {
        return BEGIN_PRIVATE + encodeBase64(privateKey.encoded) + END_PRIVATE
    }
}