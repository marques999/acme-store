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
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

import java.util.Date

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

class AcmeStore : Application() {

    /**
     */
    lateinit var api: AcmeProvider
    lateinit var privateKey: String

    /**
     */
    private lateinit var preferences: SharedPreferences
    private lateinit var cryptography: CryptographyProvider

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        privateKey = preferences.getString(PREF_PRIVATE, DEFAULT_PRIVATE)
        cryptography = CryptographyProvider(privateKey)
    }

    /**
     */
    fun initializeApi(username: String, sessionJwt: SessionJwt) {
        api = AcmeProvider(Session(sessionJwt, username), cryptography)
    }

    /**
     */
    fun saveCustomer(
        credentials: Authentication,
        privateKey: PrivateKey
    ) = preferences.edit().apply {
        putString(PREF_USERNAME, credentials.username)
        putString(PREF_PASSWORD, credentials.password)
        putString(PREF_PRIVATE, cryptography.encodePrivate(privateKey))
    }.apply()

    /**
     */
    fun loadCustomer() = Authentication(
        preferences.getString(PREF_USERNAME, DEFAULT_USERNAME),
        preferences.getString(PREF_PASSWORD, DEFAULT_PASSWORD)
    )

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
        val SERVER_URL = "http://192.168.1.87:3333/"
        val DEFAULT_USERNAME = "marques999"
        val DEFAULT_PASSWORD = "r0wsauce"
        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val ZXING_URL = "market://details?id=$ZXING_PACKAGE"

        val jsonSerializer: Moshi by lazy {
            Moshi.Builder().add(
                Date::class.java,
                Rfc3339DateJsonAdapter().nullSafe()
            ).build()
        }

        fun fetchColor(context: Context, @ColorRes color: Int): Int {
            return ContextCompat.getColor(context, color)
        }

        val DEFAULT_PRIVATE = """-----BEGIN PRIVATE KEY-----
MIIBAgIBADANBgkqhkiG9w0BAQEFAASB7TCB6gIBAAIvAL1L9h1N9xqNe0I4ddyjKD6lv0ArcEhB
JbU550urvmvJqa1Rm8Zr+V0+VCp9swcCAwEAAQIuQMQ3rekaDaywqoSU1uu//kdJe1Qhc6a2yGVi
IGzGWDohXDFi/BBaon6D5fJL6QIYAPlTZgR1+jRKpQ9SD687Y4+J+hzwtE+DAhgAwl0wx2zRyIuc
cFySxq3/scDNwAF3Ey0CFybw+7IeqyGXtwgZjRGVeQtmRYZXohH5AhgAg3MJQWaMPqiFJczGC460
BmCSBlA3WwUCGADa450Hyr7r45CqM8xJkERvv3ZuJonBVQ==
-----END PRIVATE KEY-----"""
    }
}