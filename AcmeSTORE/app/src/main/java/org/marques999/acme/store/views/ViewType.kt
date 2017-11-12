package org.marques999.acme.store.views

interface ViewType {

    fun getViewType(): Int

    companion object {

        /**
         *  ShoppingCartFragment
         */
        val SHOPPING_CART_PRODUCT = 10

        /**
         *  OrderHistoryFragment
         */
        val ORDER_HISTORY_ORDER = 20

        /**
         *  OrderViewActivity
         */
        val ORDER_VIEW_ORDER = 30
        val ORDER_VIEW_PRODUCT = 31
    }
}