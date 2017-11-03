package org.marques999.acme.store

import android.app.Application

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

import android.content.Context
import android.content.SharedPreferences

import android.util.Base64

import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.CryptographyProvider

class AcmeStore : Application() {

    /**
     */
    lateinit var acmeApi: AcmeProvider
    lateinit var preferences: SharedPreferences
    lateinit var cryptoApi: CryptographyProvider

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
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
    fun loadCustomer(): Authentication {

        cryptoApi = CryptographyProvider(
            preferences.getString(KEY_PRIVATE, R.string.default_private)
        )

        return Authentication(
            preferences.getString(KEY_USERNAME, R.string.default_username),
            preferences.getString(KEY_PASSWORD, R.string.default_password)
        )
    }

    /**
     */
    fun generateKeypair(): KeyPair = KeyPairGenerator.getInstance("RSA").apply {
        initialize(368)
    }.generateKeyPair()

    /**
     */
    companion object {
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