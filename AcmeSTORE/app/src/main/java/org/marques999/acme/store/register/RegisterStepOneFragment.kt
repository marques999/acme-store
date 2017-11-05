package org.marques999.acme.store.register

import android.content.Context
import android.os.Bundle

import java.util.Date

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_register_step1.*

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.customers.CreditCard
import org.marques999.acme.store.customers.CustomerPOST

import android.support.v4.app.Fragment

class RegisterStepOneFragment : Fragment() {

    /**
     */
    interface StepOneListener {
        fun nextPage(customerPOST: CustomerPOST)
    }

    /**
     */
    private var listener: StepOneListener? = null

    /**
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as StepOneListener
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     */
    private val dummyCard = CreditCard("", "", Date())

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_register_step1, container, false
    )

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        registerActivity_next.setOnClickListener {

            if (validateForm()) {

                listener?.nextPage(CustomerPOST(
                    registerActivity_name.text.toString(),
                    registerActivity_username.text.toString(),
                    registerActivity_password.text.toString(),
                    registerActivity_address1.text.toString(),
                    registerActivity_address2.text.toString(),
                    "PT",
                    registerActivity_nif.text.toString(),
                    "",
                    dummyCard
                ))
            }
        }
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
                registerActivity_confirm.error = AcmeStore.ERROR_MISMATCH
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
            taxNumber.length != AcmeStore.NIF_LENGTH -> {
                registerActivity_nif.error = AcmeStore.ERROR_NIF
                formValid = false
            }
            else -> {
                registerActivity_nif.error = null
            }
        }

        return formValid
    }
}