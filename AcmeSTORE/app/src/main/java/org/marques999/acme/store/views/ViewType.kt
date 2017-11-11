package org.marques999.acme.store.views

interface ViewType {

    fun getViewType(): Int

    companion object {
        val PRODUCTS = 1
        val ORDERS = 3
        val LOADING = 2
    }
}