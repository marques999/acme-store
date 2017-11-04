package org.marques999.acme.store

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

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

    private val launchRegister = View.OnClickListener {
        //startActivityForResult(Intent(applicationContext, RegisterActivity::class.java), REQUEST_REGISTER)
        finish()
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    private lateinit var acmeInstance: AcmeStore

    /**
     */
    private fun onLogin(username: String) = Consumer<SessionJwt> {
        progressDialog.dismiss()
        acmeInstance.initializeApi(username, it)
        AcmeDialogs.buildOk(this, R.string.main_connectionEstablished).show()
        loginActivity_login.isEnabled = true
        finish()
    }

    /**
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    /**
     */
    private fun authenticate(authentication: Authentication) = AuthenticationProvider().login(
        authentication
    ).observeOn(
        AndroidSchedulers.mainThread()
    ).subscribeOn(
        Schedulers.io()
    ).subscribe(
        onLogin(authentication.username), HttpErrorHandler(this).also {
        progressDialog.dismiss()
        loginActivity_login.isEnabled = true
    })

    private lateinit var progressDialog: ProgressDialog

    private val authenticateCustomer = View.OnClickListener {

        if (!validateForm()) {
            return@OnClickListener
        }

        loginActivity_login.isEnabled = false
        progressDialog.show()

        authenticate(Authentication(
            loginActivity_username.text.toString(),
            loginActivity_password.text.toString()
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        acmeInstance = application as AcmeStore
        loginActivity_register.setOnClickListener(launchRegister)
        loginActivity_login.setOnClickListener(authenticateCustomer)

        progressDialog = ProgressDialog(this, R.style.AppTheme_Dark).apply {
            isIndeterminate = true
            setMessage("Authenticating...")
        }

        acmeInstance.loadCustomer().let {
            loginActivity_username.setText(it.username)
            loginActivity_password.setText(it.password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_REGISTER && resultCode == Activity.RESULT_OK) {
            this.finish()
        }
    }

    private fun validateForm(): Boolean {

        var formValid = true

        if (loginActivity_username.text.toString().isEmpty()) {
            loginActivity_username.error = "This field is required."
            formValid = false
        } else {
            loginActivity_username.error = null
        }

        val password = loginActivity_password.text.toString()

        if (password.isEmpty()) {
            loginActivity_password.error = "This field is required."
            formValid = false
        } else if (password.length < 6 || password.length > 16) {
            loginActivity_password.error = "Password must be between 6 and 16 alphanumeric characters."
            formValid = false
        } else {
            loginActivity_password.error = null
        }

        return formValid
    }

    companion object {
        private val REQUEST_REGISTER = 0
    }
}