package org.marques999.acme.store.views.cart

interface ShoppingCartListener {
    fun onItemDeleted(barcode: String)
    fun onItemUpdated(barcode: String, delta: Int)
    fun onItemSelected(barcode: String)
}