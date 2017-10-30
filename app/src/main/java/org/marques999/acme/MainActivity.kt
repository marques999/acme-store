package org.marques999.acme

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.api.*
import org.marques999.acme.common.HttpErrorHandler
import org.marques999.acme.model.Session
import org.marques999.acme.model.Token
import org.marques999.acme.view.ProductFragment
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences



class MainActivity : AppCompatActivity() {

    /**
     */
    private lateinit var session: Session
    private lateinit var application: AcmeStore

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        application = getApplication() as AcmeStore
        session = Session("marques999", null)
        application.authenticationApi
            .login(session.username, "admin")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onAuthorized, HttpErrorHandler(this))
    }

    /**
     */
    private fun changeFragment(fragment: Fragment, cleanStack: Boolean = false) {

        val transaction = fragmentManager.beginTransaction()

        if (cleanStack) {
            clearBackStack()
        }

        transaction.replace(R.id.activity_base_content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     */
    private fun clearBackStack() {

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
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

    /**
     */
    private val onAuthorized = Consumer<Token> {
        session.token = it.token
        application.alerts.showOk("Authorized", it.token)
        application.acmeApi = AcmeProvider(session, application.cryptoApi).provideAcme()
        changeFragment(ProductFragment())
    }
}