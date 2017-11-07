package org.marques999.acme.store

import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.common.Session
import org.marques999.acme.store.common.SessionJwt

import java.security.PrivateKey

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.CryptographyProvider

import android.content.Context
import android.content.SharedPreferences

import java.text.NumberFormat

import android.app.Activity
import android.app.Application
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

import java.util.Date
import java.util.Currency
import java.util.Locale

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter

class AcmeStore : Application() {

    /**
     */
    lateinit var acmeApi: AcmeProvider

    /**
     */
    private lateinit var preferences: SharedPreferences
    private lateinit var cryptography: CryptographyProvider

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        cryptography = CryptographyProvider(preferences.getString(PREF_PRIVATE, DEFAULT_PRIVATE))
    }

    /**
     */
    fun initializeApi(username: String, sessionJwt: SessionJwt) {
        acmeApi = AcmeProvider(Session(sessionJwt, username), cryptography)
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

        private val USERNAME_MINIMUM = 3
        private val PASSWORD_MINIMUM = 6
        private val PASSWORD_MAXIMUM = 16

        val NIF_LENGTH = 9
        val ERROR_MISMATCH = "The passwords you entered do not match!"
        val ERROR_NIF = "The tax number must be exactly $NIF_LENGTH digits long."
        val ERROR_PASSWORD = "Password must have between $PASSWORD_MINIMUM and $PASSWORD_MAXIMUM alphanumeric characters"
        val ERROR_USERNAME = "Username must be at least $USERNAME_MINIMUM characters long."
        val ERROR_REQUIRED = "This field is required."

        val REQUEST_LOGIN = 1
        val REQUEST_REGISTER = 2
        val REQUEST_SCAN = 3

        val ALGORITHM_PKCS = "RSA"
        val ALGORITHM_HASH = "SHA1WithRSA"
        val SERVER_URL = "http://10.0.2.2:3333/"
        val DEFAULT_USERNAME = "marques999"
        val DEFAULT_PASSWORD = "r0wsauce"
        val ZXING_ACTIVITY = "$ZXING_PACKAGE.SCAN"
        val RAMEN_RECIPE = "mieic@feup#2017".toByteArray()
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

        fun invalidUsername(username: String): Boolean {
            return username.length < AcmeStore.USERNAME_MINIMUM
        }

        fun activitySucceeded(requestCode: Int, resultCode: Int, initialRequest: Int): Boolean {
            return requestCode == initialRequest && resultCode == Activity.RESULT_OK
        }

        fun invalidPassword(password: String): Boolean {
            return password.length < AcmeStore.PASSWORD_MINIMUM || password.length > AcmeStore.PASSWORD_MAXIMUM
        }

        fun formatCurrency(value: Double): String = NumberFormat.getCurrencyInstance(
            Locale.getDefault()
        ).apply {
            currency = Currency.getInstance("EUR")
        }.format(value)

        val DEFAULT_PRIVATE = """-----BEGIN PRIVATE KEY-----
MIIBAgIBADANBgkqhkiG9w0BAQEFAASB7TCB6gIBAAIvAL1L9h1N9xqNe0I4ddyjKD6lv0ArcEhB
JbU550urvmvJqa1Rm8Zr+V0+VCp9swcCAwEAAQIuQMQ3rekaDaywqoSU1uu//kdJe1Qhc6a2yGVi
IGzGWDohXDFi/BBaon6D5fJL6QIYAPlTZgR1+jRKpQ9SD687Y4+J+hzwtE+DAhgAwl0wx2zRyIuc
cFySxq3/scDNwAF3Ey0CFybw+7IeqyGXtwgZjRGVeQtmRYZXohH5AhgAg3MJQWaMPqiFJczGC460
BmCSBlA3WwUCGADa450Hyr7r45CqM8xJkERvv3ZuJonBVQ==
-----END PRIVATE KEY-----"""
    }
}