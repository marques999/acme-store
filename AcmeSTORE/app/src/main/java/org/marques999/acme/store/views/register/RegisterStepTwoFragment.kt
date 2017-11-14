package org.marques999.acme.store.views.register

import android.os.Bundle
import android.content.Context
import android.support.v4.app.Fragment

import java.util.Calendar

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import kotlinx.android.synthetic.main.fragment_register_step2.*

import android.widget.DatePicker
import android.widget.ArrayAdapter

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.views.register.RegisterConstants.generateError

class RegisterStepTwoFragment : Fragment(), DatePicker.OnDateChangedListener {

    /**
     */
    private var validity = Calendar.getInstance()
    private var stepTwoListener: RegisterStepTwoListener? = null

    /**
     */
    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        validity.set(year, monthOfYear, dayOfMonth)
    }

    /**
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        stepTwoListener = context as? RegisterStepTwoListener
    }

    /**
     */
    override fun onDetach() {
        super.onDetach()
        stepTwoListener = null
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

        registerActivity_ccDate.init(
            validity.get(Calendar.YEAR),
            validity.get(Calendar.MONTH),
            validity.get(Calendar.DAY_OF_MONTH),
            this
        )

        registerActivity_register.setOnClickListener {

            if (validateForm()) {

                stepTwoListener?.submitCustomer(mapOf(
                    "validity" to validity,
                    "type" to registerActivity_ccType.selectedItem.toString(),
                    "number" to registerActivity_ccNumber.text.toString()
                ))
            }
        }

        registerActivity_ccType.adapter = ArrayAdapter.createFromResource(
            context,
            R.array.register_cards,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        registerActivity_previous.setOnClickListener {
            stepTwoListener?.previousPage()
        }
    }

    /**
     */
    private fun validateForm(): Boolean {

        var formValid = true
        val cardNumber = registerActivity_ccNumber.text.toString()

        when {
            cardNumber.isEmpty() -> {
                registerActivity_ccNumber.error = generateError(R.string.error_required)
                formValid = false
            }
            RegisterConstants.invalidCard(cardNumber) -> {
                formValid = false
                registerActivity_ccNumber.error = generateError(R.string.error_card)
            }
            else -> {
                registerActivity_ccNumber.error = null
            }
        }

        if (formValid && validity.before(Calendar.getInstance())) {
            formValid = false
            AcmeDialogs.buildOk(context, R.string.error_expired).show()
        }

        return formValid
    }
}