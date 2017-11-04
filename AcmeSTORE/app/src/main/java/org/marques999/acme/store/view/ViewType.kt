package org.marques999.acme.store.view

interface ViewType {

    fun getViewType(): Int

    companion object {
        val PRODUCTS = 1
        val LOADING = 2
        val ORDER = 3
    }
}