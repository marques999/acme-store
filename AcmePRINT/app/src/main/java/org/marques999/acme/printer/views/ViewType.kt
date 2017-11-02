package org.marques999.acme.printer.views

interface ViewType {

    fun getViewType(): Int

    companion object {
        val ORDER = 1
        val PRODUCT = 2
        val CUSTOMER = 3
        val CREDIT_CARD = 4
    }
}