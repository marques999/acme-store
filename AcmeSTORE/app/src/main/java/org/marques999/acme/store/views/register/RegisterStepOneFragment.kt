package org.marques999.acme.store.views.register

import android.os.Bundle
import android.content.Context
import android.support.v4.app.Fragment

import kotlinx.android.synthetic.main.fragment_register_step1.*

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import org.marques999.acme.store.R
import org.marques999.acme.store.views.register.RegisterConstants.generateError

class RegisterStepOneFragment : Fragment() {

    /**
     */
    private var listener: RegisterStepOneListener? = null

    /**
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? RegisterStepOneListener
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
    override fun onSaveInstanceState(outState: Bundle?) {

        super.onSaveInstanceState(outState)

        outState?.putStringArrayList(BUNDLE_REGISTER1, arrayListOf(
            registerActivity_nif.text.toString(),
            registerActivity_name.text.toString(),
            registerActivity_address1.text.toString(),
            registerActivity_address2.text.toString(),
            registerActivity_password.text.toString(),
            registerActivity_username.text.toString()
        ))
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        savedInstanceState?.getStringArrayList(BUNDLE_REGISTER1)?.let {
            registerActivity_nif.setText(it[0])
            registerActivity_name.setText(it[1])
            registerActivity_address1.setText(it[2])
            registerActivity_address2.setText(it[3])
            registerActivity_password.setText(it[4])
            registerActivity_username.setText(it[5])
        }

        registerActivity_next.setOnClickListener {

            if (validateForm()) {

                listener?.nextPage(mapOf(
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
            registerActivity_name.error = generateError(R.string.error_required)
            formValid = false
        } else {
            registerActivity_name.error = null
        }

        val username = registerActivity_username.text.toString()

        when {
            username.isEmpty() -> {
                registerActivity_username.error = generateError(R.string.error_required)
                formValid = false
            }
            RegisterConstants.invalidUsername(username) -> {
                registerActivity_username.error = generateError(R.string.error_username)
                formValid = false
            }
            else -> {
                registerActivity_username.error = null
            }
        }

        val password = registerActivity_password.text.toString()

        when {
            password.isEmpty() -> {
                registerActivity_password.error = generateError(R.string.error_required)
                formValid = false
            }
            RegisterConstants.invalidPassword(password) -> {
                registerActivity_password.error = generateError(R.string.error_password)
                formValid = false
            }
            else -> {
                registerActivity_password.error = null
            }
        }

        val confirmPassword = registerActivity_confirm.text.toString()

        when {
            confirmPassword.isEmpty() -> {
                registerActivity_confirm.error = generateError(R.string.error_required)
                formValid = false
            }
            RegisterConstants.invalidPassword(confirmPassword) -> {
                registerActivity_password.error = generateError(R.string.error_password)
                formValid = false
            }
            confirmPassword != password -> {
                registerActivity_confirm.error = generateError(R.string.error_mismatch)
                formValid = false
            }
            else -> {
                registerActivity_confirm.error = null
            }
        }

        val address1 = registerActivity_address1.text.toString()

        if (address1.isEmpty()) {
            registerActivity_address1.error = generateError(R.string.error_required)
            formValid = false
        } else {
            registerActivity_address1.error = null
        }

        val address2 = registerActivity_address2.text.toString()

        if (address2.isEmpty()) {
            registerActivity_address2.error = generateError(R.string.error_required)
            formValid = false
        } else {
            registerActivity_address2.error = null
        }

        val taxNumber = registerActivity_nif.text.toString()

        when {
            taxNumber.isEmpty() -> {
                registerActivity_nif.error = generateError(R.string.error_required)
                formValid = false
            }
            RegisterConstants.invalidNif(taxNumber) -> {
                registerActivity_nif.error = generateError(R.string.error_nif)
                formValid = false
            }
            else -> {
                registerActivity_nif.error = null
            }
        }

        return formValid
    }

    companion object {
        private val BUNDLE_REGISTER1 = "org.marques999.acme.bundle.REGISTER1"
    }
}