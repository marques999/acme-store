package org.marques999.acme.store

import java.text.NumberFormat

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.CryptographyProvider

import java.security.PrivateKey

import android.content.Context
import android.content.SharedPreferences

import org.marques999.acme.store.common.Authentication

import java.util.Currency
import java.util.Locale

import android.app.Application

class AcmeStore : Application() {

    /**
     */
    lateinit var acmeApi: AcmeProvider
    lateinit var cryptoApi: CryptographyProvider

    /**
     */
    private lateinit var preferences: SharedPreferences

    /**
     */
    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        cryptoApi = CryptographyProvider(preferences.getString(PREF_PRIVATE, DEFAULT_PRIVATE))
    }

    /**
     */
    fun saveCustomer(
        credentials: Authentication,
        privateKey: PrivateKey
    ) = preferences.edit().apply {
        putString(PREF_USERNAME, credentials.username)
        putString(PREF_PASSWORD, credentials.password)
        putString(PREF_PRIVATE, cryptoApi.encodePrivate(privateKey))
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

        private val KEY_PREFERENCES = "acmestore"
        private val PREF_PRIVATE = "private"
        private val PREF_USERNAME = "username"
        private val PREF_PASSWORD = "password"

        val ALGORITHM_PKCS = "RSA"
        val ALGORITHM_HASH = "SHA1WithRSA"
        val SERVER_BASEURL = "http://192.168.1.87:3333/"
        val DEFAULT_USERNAME = "marques999"
        val DEFAULT_PASSWORD = "r0wsauce"

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