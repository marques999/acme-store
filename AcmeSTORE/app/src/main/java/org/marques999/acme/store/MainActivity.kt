package org.marques999.acme.store

import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.common.HttpErrorHandler

import android.app.Fragment

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.AuthenticationProvider

import android.support.v7.app.AppCompatActivity

import org.marques999.acme.store.customers.Session
import org.marques999.acme.store.customers.SessionJwt

import android.os.Bundle

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.store.products.ProductsFragment

import kotlinx.android.synthetic.main.activity_main.*
import org.marques999.acme.store.dummy.DummyFragment

class MainActivity : AppCompatActivity() {

    /**
     */
    private lateinit var application: AcmeStore

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleBottomNavigation()
        application = getApplication() as AcmeStore
        authenticateCustomer(application.loadCustomer())
    }

    /**
     */
    private fun handleBottomNavigation() {

        bottom_navigation.setOnNavigationItemSelectedListener({

            when (it.itemId) {
                R.id.action_favorites -> changeFragment(ProductsFragment())
                R.id.action_schedules -> changeFragment(DummyFragment())
                R.id.action_music -> changeFragment(ButtonFragment())
            }
            true
        })
    }

    /**
     */
    private fun onLogin(username: String) = Consumer<SessionJwt> {
        application.acmeApi = AcmeProvider(Session(it, username), application.cryptoApi)
        AcmeDialogs.buildOk(this, R.string.main_connected).show()
        changeFragment(ProductsFragment())
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