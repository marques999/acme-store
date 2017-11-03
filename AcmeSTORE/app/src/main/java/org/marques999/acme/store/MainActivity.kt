package org.marques999.acme.store

import android.os.Bundle

import android.app.Fragment
import android.app.FragmentManager

import android.support.v7.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.common.Session
import org.marques999.acme.store.common.Token

import org.marques999.acme.store.view.ProductFragment

import org.marques999.acme.store.api.AcmeProvider
import org.marques999.acme.store.api.AuthenticationProvider

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
        authenticateCustomer("admin", "admin")
    }

    /**
     */
    private fun onLogin(username: String) = Consumer<Token> {
        AcmeDialogs.showOk(this, "Authorized", it.token)
        application.acmeApi = AcmeProvider(Session(username, it.token), application.cryptoApi)
        changeFragment(ProductFragment())
    }

    /**
     */
    private fun authenticateCustomer(username: String, password: String) = AuthenticationProvider()
        .login(username, password)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(onLogin(username), HttpErrorHandler(this))

    /**
     */
    private fun changeFragment(fragment: Fragment, cleanStack: Boolean = false) {

        val fm = fragmentManager
        val fragmentTransaction = fm.beginTransaction()

        if (cleanStack && fm.backStackEntryCount > 0) {
            fm.popBackStack(fm.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        fragmentTransaction.replace(R.id.activity_base_content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     */
    override fun onBackPressed() {

        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}