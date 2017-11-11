package org.marques999.acme.store.views.register

import android.os.Bundle
import android.content.Context
import android.support.v4.app.Fragment

import kotlinx.android.synthetic.main.fragment_register_step1.*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.marques999.acme.store.R
import org.marques999.acme.store.views.register.RegisterConstants.generateError

class RegisterStepOneFragment : Fragment() {

    /**
     */
    interface StepOneListener {
        fun nextPage(parameters: Map<String, Any>)
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

                listener?.nextPage(mapOf(
                    "country" to "PT",
                    "name" to registerActivity_name.text.toString(),
                    "tax_number" to registerActivity_nif.text.toString(),
                    "username" to registerActivity_username.text.toString(),
                    "password" to registerActivity_password.text.toString(),
                    "address1" to registerActivity_address1.text.toString(),
                    "address2" to registerActivity_address2.text.toString()
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
            registerActivity_name.error = generateError(R.string.errorRequired)
            formValid = false
        } else {
            registerActivity_name.error = null
        }

        val username = registerActivity_username.text.toString()

        when {
            username.isEmpty() -> {
                registerActivity_username.error = generateError(R.string.errorRequired)
                formValid = false
            }
            RegisterConstants.invalidUsername(username) -> {
                registerActivity_username.error = generateError(R.string.errorUsername)
                formValid = false
            }
            else -> {
                registerActivity_username.error = null
            }
        }

        val password = registerActivity_password.text.toString()

        when {
            password.isEmpty() -> {
                registerActivity_password.error = generateError(R.string.errorRequired)
                formValid = false
            }
            RegisterConstants.invalidPassword(password) -> {
                registerActivity_password.error = generateError(R.string.errorPassword)
                formValid = false
            }
            else -> {
                registerActivity_password.error = null
            }
        }

        val confirmPassword = registerActivity_confirm.text.toString()

        when {
            confirmPassword.isEmpty() -> {
                registerActivity_confirm.error = generateError(R.string.errorRequired)
                formValid = false
            }
            RegisterConstants.invalidPassword(confirmPassword) -> {
                registerActivity_password.error = generateError(R.string.errorPassword)
                formValid = false
            }
            confirmPassword != password -> {
                registerActivity_confirm.error = generateError(R.string.errorMismatch)
                formValid = false
            }
            else -> {
                registerActivity_confirm.error = null
            }
        }

        val address1 = registerActivity_address1.text.toString()

        if (address1.isEmpty()) {
            registerActivity_address1.error = generateError(R.string.errorRequired)
            formValid = false
        } else {
            registerActivity_address1.error = null
        }

        val address2 = registerActivity_address2.text.toString()

        if (address2.isEmpty()) {
            registerActivity_address2.error = generateError(R.string.errorRequired)
            formValid = false
        } else {
            registerActivity_address2.error = null
        }

        val taxNumber = registerActivity_nif.text.toString()

        when {
            taxNumber.isEmpty() -> {
                registerActivity_nif.error = generateError(R.string.errorRequired)
                formValid = false
            }
            RegisterConstants.invalidNif(taxNumber) -> {
                registerActivity_nif.error = generateError(R.string.errorNif)
                formValid = false
            }
            else -> {
                registerActivity_nif.error = null
            }
        }

        return formValid
    }
}