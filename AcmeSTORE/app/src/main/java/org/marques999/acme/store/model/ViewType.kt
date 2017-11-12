package org.marques999.acme.store.model

interface ViewType {

    /**
     */
    fun getViewType(): Int

    /**
     */
    companion object {

        /**
         *  OrderViewActivity
         */
        val ORDER_VIEW_ORDER = 30
        val ORDER_VIEW_PRODUCT = 31
    }
}