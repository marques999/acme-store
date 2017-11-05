package org.marques999.acme.store.register

import android.content.Context
import android.os.Bundle

import java.util.Date

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_register_step2.*

import org.marques999.acme.store.AcmeStore
import org.marques999.acme.store.R
import org.marques999.acme.store.customers.CreditCard

import android.support.v4.app.Fragment

class RegisterStepTwoFragment : Fragment() {

    /**
     */
    private var listener: StepTwoListener? = null

    /**
     */
    interface StepTwoListener {
        fun previousPage()
        fun submitCustomer(creditCard: CreditCard)
    }

    /**
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as StepTwoListener
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
        R.layout.fragment_register_step2, container, false
    )

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        registerActivity_register.setOnClickListener {

            if (validateForm()) {

                listener?.submitCustomer(CreditCard(
                    registerActivity_ccType.text.toString(),
                    registerActivity_ccNumber.text.toString(),
                    Date()
                ))
            }
        }

        registerActivity_previous.setOnClickListener {
            listener?.previousPage()
        }
    }

    /**
     */
    private fun validateForm(): Boolean {

        var formValid = true
        val creditCardType = registerActivity_ccType.text.toString()

        if (creditCardType.isEmpty()) {
            registerActivity_ccType.error = AcmeStore.ERROR_REQUIRED
            formValid = false
        } else {
            registerActivity_ccType.error = null
        }

        val creditCardNumber = registerActivity_ccNumber.text.toString()

        when {
            creditCardNumber.isEmpty() -> {
                registerActivity_ccNumber.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            creditCardNumber.length != 12 -> {
                registerActivity_ccNumber.error = AcmeStore.ERROR_NIF
                formValid = false
            }
            else -> {
                registerActivity_ccNumber.error = null
            }
        }

        val creditCardValidity = registerActivity_ccDate.text.toString()

        when {
            creditCardValidity.isEmpty() -> {
                registerActivity_ccDate.error = AcmeStore.ERROR_REQUIRED
                formValid = false
            }
            else -> {
                registerActivity_ccDate.error = null
            }
        }

        return formValid
    }
}