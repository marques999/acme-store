package org.marques999.acme.store.views.register

import android.os.Bundle
import android.util.Base64
import android.content.Intent
import android.app.ProgressDialog

import java.security.KeyPairGenerator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.api.AuthenticationProvider
import org.marques999.acme.store.common.HttpErrorHandler

import kotlinx.android.synthetic.main.fragment_register_step2.*

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.fasterxml.jackson.databind.ObjectMapper

import org.marques999.acme.store.model.Customer
import org.marques999.acme.store.model.CustomerPOST
import org.marques999.acme.store.model.Authentication

class RegisterActivity : AppCompatActivity(), RegisterStepOneListener, RegisterStepTwoListener {

    /**
     */
    private val customer = HashMap<String, Any>()
    private val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()

    /**
     */
    private lateinit var acmeInstance: AcmeStore
    private lateinit var progressDialog: ProgressDialog
    private lateinit var registerStepOne: RegisterStepOneFragment
    private lateinit var registerStepTwo: RegisterStepTwoFragment

    /**
     */
    override fun submitCustomer(parameters: Map<String, Any>) {

        customer.put("credit_card", parameters)
        customer.put("public_key", Base64.encodeToString(keyPair.public.encoded, Base64.DEFAULT))
        registerActivity_register.isEnabled = false
        progressDialog.show()

        val customerPOST = ObjectMapper().convertValue(customer, CustomerPOST::class.java)

        AuthenticationProvider().register(customerPOST).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            onRegisterComplete(Authentication(customerPOST.username, customerPOST.password)),
            onRegisterFailed(HttpErrorHandler(this))
        )
    }

    /**
     */
    override fun previousPage() {
        changeFragment(registerStepOne, true)
    }

    /**
     */
    override fun nextPage(parameters: Map<String, Any>) {
        customer.putAll(parameters)
        changeFragment(registerStepTwo, true)
    }

    /**
     */
    private fun onRegisterComplete(authentication: Authentication) = Consumer<Customer> {

        Intent().putExtra(
            RegisterConstants.EXTRA_USERNAME, authentication.username
        ).putExtra(
            RegisterConstants.EXTRA_PASSWORD, authentication.password
        ).let {
            (application as AcmeStore).saveCustomer(authentication, keyPair.private)
            setResult(AppCompatActivity.RESULT_OK, it)
            finish()
        }
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
        registerStepOne = RegisterStepOneFragment()
        registerStepTwo = RegisterStepTwoFragment()
        progressDialog = AcmeDialogs.buildProgress(this, R.string.loginActivity_progress)
        changeFragment(registerStepOne, false)
    }

    /**
     */
    private fun changeFragment(frag: Fragment, saveInBackstack: Boolean) {

        val backStateName = frag.javaClass.name

        if (supportFragmentManager.popBackStackImmediate(backStateName, 0) ||
            supportFragmentManager.findFragmentByTag(backStateName) != null) {
            return
        }

        supportFragmentManager.beginTransaction().apply {

            setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )

            replace(R.id.registerActivity_content, frag, backStateName)

            if (saveInBackstack) {
                addToBackStack(backStateName)
            }
        }.commit()
    }
}