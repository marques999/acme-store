package org.marques999.acme.store

import android.os.Bundle
import android.content.Intent
import android.app.ProgressDialog
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.View

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import org.marques999.acme.store.api.HttpErrorHandler
import org.marques999.acme.store.api.AuthenticationProvider

import kotlinx.android.synthetic.main.activity_login.*

import org.marques999.acme.store.model.Authentication
import org.marques999.acme.store.views.register.RegisterActivity

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
        authentication: Authentication
    ) = AuthenticationProvider().login(
        authentication
    ).observeOn(
        AndroidSchedulers.mainThread()
    ).subscribeOn(
        Schedulers.io()
    ).subscribe({
        progressDialog.dismiss()
        login_buttonLogin.isEnabled = true
        (application as AcmeStore).initializeApi(authentication.username, it)
        launchMain()
    }, {
        progressDialog.dismiss()
        login_buttonLogin.isEnabled = true
        HttpErrorHandler(this).accept(it)
    })

    /**
     */
    private fun validateRequired(textInputEditText: TextInputEditText): Boolean {

        textInputEditText.error = null

        if (textInputEditText.text.toString().isEmpty()) {
            textInputEditText.error = getString(R.string.error_required)
        } else {
            return true
        }

        return false
    }

    /**
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null &&
            resultCode == AppCompatActivity.RESULT_OK &&
            requestCode == AcmeStore.REQUEST_REGISTER) {
            updateCustomer(data.getParcelableExtra(EXTRA_AUTHENTICATION))
        }
    }

    /**
     */
    private val actionRegister = View.OnClickListener {

        Intent(applicationContext, RegisterActivity::class.java).let {
            startActivityForResult(it, AcmeStore.REQUEST_REGISTER)
        }
    }

    /**
     */
    private val actionLogin = View.OnClickListener {

        if (validateRequired(login_username) && validateRequired(login_password)) {
            login_buttonLogin.isEnabled = false
            progressDialog.show()
            authenticate(Authentication(
                login_username.text.toString(),
                login_password.text.toString()
            ))
        }
    }

    /**
     */
    private fun updateCustomer(authentication: Authentication) {
        login_password.isEnabled = true
        login_buttonReset.visibility = View.VISIBLE
        login_username.setText(authentication.username)
        login_password.setText(authentication.password)
        login_buttonLogin.setOnClickListener(actionLogin)
        login_buttonLogin.text = getString(R.string.login_button)
    }

    /**
     */
    private fun resetCredentials() {

        login_password.isEnabled = false
        login_buttonReset.visibility = View.GONE
        login_buttonLogin.setOnClickListener(actionRegister)
        login_buttonLogin.text = getString(R.string.register_button)

        (application as AcmeStore).loadCustomer().also {
            login_username.setText(it.username)
            login_password.setText(it.password)
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialog = AcmeDialogs.buildProgress(this, R.string.login_progress)

        if ((application as AcmeStore).firstRun()) {
            resetCredentials()
        } else {
            login_buttonLogin.setOnClickListener(actionLogin)
            login_username.setText((application as AcmeStore).loadCustomer().username)
        }

        login_buttonReset.setOnClickListener {
            (application as AcmeStore).forgetCustomer()
            resetCredentials()
        }
    }

    /**
     */
    companion object {
        internal val EXTRA_AUTHENTICATION = "org.marques999.acme.extras.AUTHENTICATION"
    }
}