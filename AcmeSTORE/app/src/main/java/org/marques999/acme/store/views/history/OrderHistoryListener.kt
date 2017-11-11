package org.marques999.acme.store.views.history

interface OrderHistoryListener {
    fun onItemSelected(token: String)
}