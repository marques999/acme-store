package org.marques999.acme.printer.model

interface ViewType {

    /**
     */
    fun getViewType(): Int

    /**
     */
    companion object {
        val DETAILS_ORDER = 1
        val DETAILS_PRODUCT = 2
        val DETAILS_CUSTOMER = 3
        val DETAILS_CREDIT_CARD = 4
    }
}