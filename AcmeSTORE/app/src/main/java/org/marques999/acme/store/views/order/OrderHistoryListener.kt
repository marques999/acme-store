package org.marques999.acme.store.views.order

interface OrderHistoryListener {
    fun onItemSelected(token: String)
}