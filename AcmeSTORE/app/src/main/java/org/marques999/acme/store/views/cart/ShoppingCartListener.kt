package org.marques999.acme.store.views.cart

import org.marques999.acme.store.model.Product

interface ShoppingCartListener {
    fun onItemChanged()
    fun onItemSelected(product: Product)
}