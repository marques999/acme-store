package org.marques999.acme.store.model

interface ViewType {

    /**
     */
    fun getViewType(): Int

    /**
     */
    companion object {
        val PROFILE_CUSTOMER = 10
        val PROFILE_CREDIT_CARD = 11
        val ORDER_VIEW_CODE = 20
        val ORDER_VIEW_ORDER = 21
        val ORDER_VIEW_PRODUCT = 22
    }
}