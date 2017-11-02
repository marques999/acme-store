package org.marques999.acme.common

interface ViewType {

    fun getViewType(): Int

    companion object {
        val PRODUCTS = 1
        val LOADING = 2
    }
}
