package org.marques999.acme.store

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity

import org.marques999.acme.store.model.Authentication

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.api.AuthenticationProvider
import org.marques999.acme.store.api.HttpErrorHandler

import kotlinx.android.synthetic.main.activity_login.*

import org.marques999.acme.store.views.register.RegisterActivity
import org.marques999.acme.store.views.register.RegisterConstants
import org.marques999.acme.store.views.register.RegisterConstants.generateError

class LoginActivity : AppCompatActivity() {

    /**
     */
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private fun launchMain() = Intent(this, MainActivity::class.java).run {
        startActivity(addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    /**
     */
    private fun authenticate(
        username: String,
        password: String
    ) = AuthenticationProvider().login(
        Authentication(username, password)
    ).observeOn(
        AndroidSchedulers.mainThread()
    ).subscribeOn(
        Schedulers.io()
    ).subscribe({
        progressDialog.dismiss()
        loginActivity_login.isEnabled = true
        (application as AcmeStore).initializeApi(username, it)
        launchMain()
    }, {
        progressDialog.dismiss()
        loginActivity_login.isEnabled = true
        HttpErrorHandler(this).accept(it)
    })

    /**
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null &&
            resultCode == AppCompatActivity.RESULT_OK &&
            requestCode == AcmeStore.REQUEST_REGISTER) {
            loginActivity_username.setText(data.getStringExtra(RegisterConstants.EXTRA_USERNAME))
            loginActivity_password.setText(data.getStringExtra(RegisterConstants.EXTRA_PASSWORD))
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialog = AcmeDialogs.buildProgress(this, R.string.login_progress)

        loginActivity_login.setOnClickListener {

            var formValid = true

            if (loginActivity_username.text.toString().isEmpty()) {
                loginActivity_username.error = generateError(R.string.error_required)
                formValid = false
            } else {
                loginActivity_username.error = null
            }

            if (loginActivity_password.text.toString().isEmpty()) {
                loginActivity_password.error = generateError(R.string.error_required)
                formValid = false
            } else {
                loginActivity_password.error = null
            }

            if (formValid) {
                loginActivity_login.isEnabled = false
                progressDialog.show()
                authenticate(loginActivity_username.text.toString(), loginActivity_password.text.toString())
            }
        }

        loginActivity_register.setOnClickListener {
            Intent(applicationContext, RegisterActivity::class.java).let {
                startActivityForResult(it, AcmeStore.REQUEST_REGISTER)
            }
        }

        (application as AcmeStore).loadCustomer().let {
            loginActivity_username.setText(it.username)
            loginActivity_password.setText(it.password)
        }
    }
}