package org.marques999.acme.store

import android.os.Bundle

import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.common.HttpErrorHandler

import android.app.Fragment

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.AuthenticationProvider

import android.support.v7.app.AppCompatActivity

import org.marques999.acme.store.customers.Session
import org.marques999.acme.store.customers.Jwt

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.store.view.ProductFragment

class MainActivity : AppCompatActivity() {

    /**
     */
    private lateinit var application: AcmeStore

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        application = getApplication() as AcmeStore
        authenticateCustomer(application.loadCustomer())
    }

    /**
     */
    private fun onLogin(username: String) = Consumer<Jwt> {
        application.acmeApi = AcmeProvider(Session(it, username), application.cryptoApi)
        AcmeDialogs.showOk(this, "Authorized", it.expire.toString())
        changeFragment(ProductFragment())
    }

    /**
     */
    private fun authenticateCustomer(authentication: Authentication) = AuthenticationProvider()
        .login(authentication)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(onLogin(authentication.username), HttpErrorHandler(this))

    /**
     */
    private fun changeFragment(fragment: Fragment) = fragmentManager.beginTransaction().apply {
        replace(R.id.activity_base_content, fragment)
        addToBackStack(null)
    }.commit()

    /**
     */
    override fun onBackPressed() = if (fragmentManager.backStackEntryCount > 1) {
        fragmentManager.popBackStack()
    } else {
        finish()
    }
}