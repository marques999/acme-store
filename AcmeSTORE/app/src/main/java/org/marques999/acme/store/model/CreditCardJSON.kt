package org.marques999.acme.store.model

class CreditCardJSON : ViewType {

    /**
     */
    lateinit var type: String
    lateinit var number: String
    lateinit var validity: java.util.Date

    /**
     */
    override fun getViewType(): Int {
        return ViewType.PROFILE_CREDIT_CARD
    }
}