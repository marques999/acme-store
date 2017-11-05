package org.marques999.acme.store

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_register.*

import org.marques999.acme.store.api.AuthenticationProvider
import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.HttpErrorHandler
import org.marques999.acme.store.customers.CreditCard
import org.marques999.acme.store.customers.Customer

import java.util.Date


class RegisterActivity : AppCompatActivity() {

    /**
     */
    private lateinit var acmeInstance: AcmeStore
    private lateinit var progressDialog: ProgressDialog

    /**
     */
    private var onRegisterComplete = Consumer<Customer> {
        registerActivity_register.isEnabled = true
        setResult(Activity.RESULT_OK, null)
        finish()
    }

    /**
     */
    private fun onRegisterFailed(next: Consumer<Throwable>) = Consumer<Throwable> {
        progressDialog.dismiss()
        registerActivity_register.isEnabled = true
        next.accept(it)
    }

    /**
     */
    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        acmeInstance = application as AcmeStore
        progressDialog = AcmeDialogs.buildProgress(this, R.string.loginActivity_progress)

        registerActivity_register.setOnClickListener {

            if (validateForm()) {
                registerCustomer()
            }
        }

        registerActivity_login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
            //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    /**
     */
    private fun registerCustomer() {

        registerActivity_register.isEnabled = false
        progressDialog.show()

        AuthenticationProvider().register(
            registerActivity_name.text.toString(),
            registerActivity_username.text.toString(),
            registerActivity_password.text.toString(),
            registerActivity_address1.text.toString(),
            registerActivity_address2.text.toString(),
            "PT",
            registerActivity_nif.text.toString(),
            "",
            CreditCard(
                "",
                "",
                Date()
            )
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onRegisterComplete,
            onRegisterFailed(HttpErrorHandler(this))
        )
    }

    /**
     */
    private fun validateForm(): Boolean {

        var formValid = true
        val name = registerActivity_name.text.toString()

        if (name.isEmpty()) {
            registerActivity_name.error = AcmeStore.ERROR_REQUIRED
            formValid = false
        } else {
            registerActivity_name.error = null
        }

        val username = registerActivity_username.text.toString()

        when {
            username.isEmpty() -> {
                registerActivity_username.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            AcmeStore.invalidUsername(username) -> {
                registerActivity_username.error = AcmeStore.ERROR_USERNAME
                formValid = false
            }
            else -> {
                registerActivity_username.error = null
            }
        }

        val password = registerActivity_password.text.toString()

        when {
            password.isEmpty() -> {
                registerActivity_password.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            AcmeStore.invalidPassword(password) -> {
                registerActivity_password.error = AcmeStore.ERROR_PASSWORD
                formValid = false
            }
            else -> {
                registerActivity_password.error = null
            }
        }

        val confirmPassword = registerActivity_confirm.text.toString()

        when {
            confirmPassword.isEmpty() -> {
                registerActivity_confirm.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            AcmeStore.invalidPassword(confirmPassword) -> {
                registerActivity_password.error = AcmeStore.ERROR_PASSWORD
                formValid = false
            }
            confirmPassword != password -> {
                registerActivity_confirm.error = ERROR_MISMATCH
                formValid = false
            }
            else -> {
                registerActivity_confirm.error = null
            }
        }

        val address1 = registerActivity_address1.text.toString()

        if (address1.isEmpty()) {
            registerActivity_address1.error = AcmeStore.ERROR_REQUIRED
            formValid = false
        } else {
            registerActivity_address1.error = null
        }

        val address2 = registerActivity_address2.text.toString()

        if (address2.isEmpty()) {
            registerActivity_address2.error = AcmeStore.ERROR_REQUIRED
            formValid = false
        } else {
            registerActivity_address2.error = null
        }

        val taxNumber = registerActivity_nif.text.toString()

        when {
            taxNumber.isEmpty() -> {
                registerActivity_nif.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            taxNumber.length != 9 -> {
                registerActivity_nif.error = ERROR_NIF
                formValid = false
            }
            else -> {
                registerActivity_nif.error = null
            }
        }

        return formValid
    }

    /**
     */
    companion object {
        private val ERROR_MISMATCH = "The passwords you entered do not match!"
        private val ERROR_NIF = "The tax number must be exactly 9 digits long."
    }
}