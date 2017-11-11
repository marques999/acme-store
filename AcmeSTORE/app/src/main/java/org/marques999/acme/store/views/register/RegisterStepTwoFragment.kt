package org.marques999.acme.store.views.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.widget.DatePicker
import android.widget.ArrayAdapter
import android.support.v4.app.Fragment
import android.util.SparseArray

import kotlinx.android.synthetic.main.fragment_register_step2.*

import org.marques999.acme.store.R
import org.marques999.acme.store.views.register.RegisterConstants.generateError
import org.marques999.acme.store.AcmeDialogs

import java.util.Calendar
import java.util.regex.Pattern

class RegisterStepTwoFragment : Fragment(), DatePicker.OnDateChangedListener {

    /**
     */
    interface StepTwoListener {
        fun previousPage()
        fun submitCustomer(parameters: Map<String, Any>)
    }

    /**
     */
    private val validity = Calendar.getInstance()

    /**
     */
    private var stepTwoListener: StepTwoListener? = null

    /**
     */
    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        validity.set(year, monthOfYear, dayOfMonth)
    }

    /**
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        stepTwoListener = context as StepTwoListener
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
    companion object {
        private val amexRegex = Pattern.compile("^3[47][0-9]{1,13}$")
        private val visaRegex = Pattern.compile("^4[0-9]{2,12}(?:[0-9]{3})?$")
        private val masterCardRegex = Pattern.compile("^5[1-5][0-9]{1,14}$")
    }

    /**
     */
    private val creditCardRegex = SparseArray<Pattern>().apply {
        put(0, visaRegex)
        put(1, masterCardRegex)
        put(2, masterCardRegex)
        put(3, visaRegex)
        put(4, amexRegex)
    }

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

                stepTwoListener?.submitCustomer(
                    mapOf(
                        "validity" to validity,
                        "type" to registerActivity_ccType.selectedItem.toString(),
                        "number" to registerActivity_ccNumber.text.toString()
                    )
                )
            }
        }

        registerActivity_ccType.adapter = ArrayAdapter.createFromResource(
            context,
            R.array.planets_array,
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
        val cardType = registerActivity_ccType.selectedItemId.toInt()

        when {
            cardNumber.isEmpty() -> {
                registerActivity_ccNumber.error = generateError(R.string.errorRequired)
                formValid = false
            }
            creditCardRegex[cardType].matcher(cardNumber).matches() -> {
                registerActivity_ccNumber.error = null
            }
            else -> {
                formValid = false
                registerActivity_ccNumber.error = getString(
                    R.string.errorNumber,
                    registerActivity_ccType.selectedItem.toString()
                )
            }
        }

        if (formValid && validity.before(Calendar.getInstance())) {
            formValid = false
            AcmeDialogs.buildOk(context, R.string.errorExpired).show()
        }

        return formValid
    }
}