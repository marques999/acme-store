package org.marques999.acme

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64

import org.marques999.acme.api.*
import org.marques999.acme.common.AcmeAlerts
import org.marques999.acme.model.Authentication

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

@SuppressLint("StringFormatInvalid")
class AcmeStore : Application() {

    /**
     */
    lateinit var acmeApi: AcmeService
    lateinit var cryptoApi: CryptographyService

    /**
     */
    private lateinit var privateKey: String
    private lateinit var preferences: SharedPreferences

    /**
     */
    override fun onCreate() {

        super.onCreate()
        preferences = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
        privateKey = preferences.getString(R.string.preferences_private, R.string.default_private)
        cryptoApi = CryptographyProvider(privateKey).provideCryptography()
    }

    /**
     */
    val alerts: AcmeAlerts = AcmeAlerts(this)
    val authenticationApi: AuthenticationService = AuthenticationProvider().provideAuthentication()

    fun registerCustomer(credentials: Authentication, privateKey: PrivateKey) {

        val sharedPreferences = preferences.edit()

        sharedPreferences.putString(R.string.preferences_username, credentials.username)
        sharedPreferences.putString(R.string.default_password, credentials.password)
        sharedPreferences.putString(R.string.default_private, encodePrivateKey(privateKey))
        sharedPreferences.apply()
    }

    fun getCredentials() = Authentication(
        preferences.getString(R.string.preferences_username, R.string.default_username),
        preferences.getString(R.string.preferences_password, R.string.default_password)
    )

    /**
     */
    private fun SharedPreferences.getString(key: Int, value: Int): String {
        return this.getString(getString(key), getString(value))
    }

    private fun SharedPreferences.Editor.putString(key: Int, value: String) {
        this.putString(getString(key), value)
    }

    /**
     */
    fun generateKeypair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(368)
        return keyPairGenerator.generateKeyPair()
    }

    /**
     */
    private fun encodePrivateKey(privateKey: PrivateKey): String {
        return "-----BEGIN PRIVATE KEY-----\n" + Base64.encodeToString(
            privateKey.encoded, Base64.DEFAULT
        ) + "-----END PRIVATE KEY-----\n"
    }

    /**
     */
    private fun encodePublicKey(publicKey: PublicKey): String {
        return "-----BEGIN PUBLIC KEY-----\n" + Base64.encodeToString(
            publicKey.encoded, Base64.DEFAULT
        ) + "-----END PUBLIC KEY-----\n"
    }
}