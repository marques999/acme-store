package org.marques999.acme.store.register

import android.app.Activity
import android.app.ProgressDialog

import org.marques999.acme.store.customers.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import android.os.Bundle

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.api.AuthenticationProvider
import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.HttpErrorHandler

import kotlinx.android.synthetic.main.fragment_register_step2.*

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(),
    RegisterStepOneFragment.StepOneListener,
    RegisterStepTwoFragment.StepTwoListener {

    /**
     */
    private lateinit var customer: CustomerPOST
    private lateinit var acmeInstance: AcmeStore
    private lateinit var progressDialog: ProgressDialog
    private lateinit var registerStepOne: RegisterStepOneFragment
    private lateinit var registerStepTwo: RegisterStepTwoFragment

    /**
     */
    override fun submitCustomer(creditCard: CreditCard) {

        customer.credit_card = creditCard
        registerActivity_register.isEnabled = false
        progressDialog.show()

        AuthenticationProvider().register(customer).observeOn(
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
    override fun previousPage() {
        changeFragment(registerStepOne, true)
    }

    /**
     */
    override fun nextPage(customerPOST: CustomerPOST) {
        customer = customerPOST
        changeFragment(registerStepTwo, true)
    }

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
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )

            replace(R.id.registerActivity_content, frag, backStateName)

            if (saveInBackstack) {
                addToBackStack(backStateName)
            }
        }.commit()
    }
}