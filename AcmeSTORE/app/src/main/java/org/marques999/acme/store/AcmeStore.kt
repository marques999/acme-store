package org.marques999.acme.store

import org.marques999.acme.store.model.Authentication
import org.marques999.acme.store.model.Session
import org.marques999.acme.store.model.SessionJwt

import java.security.PrivateKey

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.CryptographyProvider

import android.content.Context
import android.content.SharedPreferences

import android.app.Application
import android.util.Base64

import java.util.Date

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import org.marques999.acme.store.model.CustomerCart

class AcmeStore : Application() {

    /**
     */
    lateinit var api: AcmeProvider

    /**
     */
    val shoppingCart = CustomerCart()

    /**
     */
    private lateinit var preferences: SharedPreferences
    private lateinit var cryptography: CryptographyProvider

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
    }

    /**
     */
    fun initializeApi(username: String, sessionJwt: SessionJwt) {
        cryptography = CryptographyProvider(preferences.getString(PREF_PRIVATE, ""))
        api = AcmeProvider(Session(sessionJwt, username), cryptography)
    }

    /**
     */
    private fun encodeBase64(bytes: ByteArray): String = Base64.encodeToString(
        bytes, Base64.DEFAULT
    )

    /**
     */
    private fun encodePrivate(privateKey: PrivateKey): String {
        return "$CERTIFICATE_BEGIN\n${encodeBase64(privateKey.encoded)}$CERTIFICATE_END\n"
    }

    /**
     */
    fun registerCustomer(
        credentials: Authentication,
        privateKey: PrivateKey
    ) = preferences.edit().apply {
        putString(PREF_USERNAME, credentials.username)
        putString(PREF_PASSWORD, credentials.password)
        putString(PREF_PRIVATE, encodePrivate(privateKey))
    }.apply()

    /**
     */
    fun forgetCustomer() = preferences.edit().apply {
        remove(PREF_USERNAME)
        remove(PREF_PASSWORD)
        remove(PREF_PRIVATE)
    }.apply()

    /**
     */
    fun loadCustomer() = Authentication(
        preferences.getString(PREF_USERNAME, "guest"),
        preferences.getString(PREF_PASSWORD, "1234567890")
    )

    /**
     */
    fun firstRun() = preferences.run {
        !(contains(PREF_USERNAME) && contains(PREF_PASSWORD) && contains(PREF_PRIVATE))
    }

    /**
     */
    companion object {

        private val PREF_PRIVATE = "private"
        private val PREF_USERNAME = "username"
        private val PREF_PASSWORD = "password"
        private val KEY_PREFERENCES = "acmestore"
        private val ZXING_PACKAGE = "com.google.zxing.client.android"

        val REQUEST_SCAN = 0
        val REQUEST_REGISTER = 1
        val ALGORITHM_PKCS = "RSA"
        val ALGORITHM_HASH = "SHA1WithRSA"
        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val SERVER_URL = "http://192.168.1.87:3333/"
        val ZXING_URL = "market://details?id=$ZXING_PACKAGE"
        val CERTIFICATE_END = "-----END PRIVATE KEY-----"
        val CERTIFICATE_BEGIN = "-----BEGIN PRIVATE KEY-----"

        val jsonSerializer: Moshi = Moshi.Builder().add(
            Date::class.java,
            Rfc3339DateJsonAdapter().nullSafe()
        ).build()
    }
}