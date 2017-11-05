package org.marques999.acme.store

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_login.*

import org.marques999.acme.store.api.AuthenticationProvider
import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.common.SessionJwt

class LoginActivity : AppCompatActivity() {

    /**
     */
    private lateinit var acmeInstance: AcmeStore
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private fun authenticate(authentication: Authentication) = AuthenticationProvider().login(
        authentication
    ).observeOn(
        AndroidSchedulers.mainThread()
    ).subscribeOn(
        Schedulers.io()
    ).subscribe(
        onLoginCompleted(authentication.username),
        onLoginFailed(HttpErrorHandler(this))
    )

    /**
     */
    private fun onLoginCompleted(username: String) = Consumer<SessionJwt> {
        progressDialog.dismiss()
        loginActivity_login.isEnabled = true
        acmeInstance.initializeApi(username, it)
        setResult(Activity.RESULT_OK, null)
        finish()
    }

    /**
     */
    private fun onLoginFailed(next: Consumer<Throwable>) = Consumer<Throwable> {
        progressDialog.dismiss()
        loginActivity_login.isEnabled = true
        next.accept(it)
    }

    /**
     */
    private fun authenticateCustomer() {

        loginActivity_login.isEnabled = false
        progressDialog.show()

        authenticate(Authentication(
            loginActivity_username.text.toString(),
            loginActivity_password.text.toString()
        ))
    }

    /**
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        acmeInstance = application as AcmeStore
        progressDialog = AcmeDialogs.buildProgress(this, R.string.loginActivity_progress)

        loginActivity_register.setOnClickListener {

            Intent(applicationContext, RegisterActivity::class.java).let {
                startActivityForResult(it, AcmeStore.REQUEST_REGISTER)
                finish()
            }
        }

        loginActivity_login.setOnClickListener {

            if (validateForm()) {
                authenticateCustomer()
            }
        }

        acmeInstance.loadCustomer().let {
            loginActivity_username.setText(it.username)
            loginActivity_password.setText(it.password)
        }
    }

    /**
     */
    override fun onActivityResult(request: Int, result: Int, data: Intent?) {

        if (AcmeStore.activitySucceeded(request, result, AcmeStore.REQUEST_REGISTER)) {
            finish()
        }
    }

    /**
     */
    private fun validateForm(): Boolean {

        var formValid = true
        val username = loginActivity_username.text.toString()

        when {
            username.isEmpty() -> {
                loginActivity_username.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            AcmeStore.invalidUsername(username) -> {
                loginActivity_username.error = AcmeStore.ERROR_USERNAME
                formValid = false
            }
            else -> {
                loginActivity_username.error = null
            }
        }

        val password = loginActivity_password.text.toString()

        when {
            password.isEmpty() -> {
                loginActivity_password.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            AcmeStore.invalidPassword(password) -> {
                loginActivity_password.error = AcmeStore.ERROR_PASSWORD
                formValid = false
            }
            else -> {
                loginActivity_password.error = null
            }
        }

        return formValid
    }
}