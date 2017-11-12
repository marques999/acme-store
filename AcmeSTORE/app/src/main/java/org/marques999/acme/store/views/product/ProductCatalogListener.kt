package org.marques999.acme.store.views.product

import org.marques999.acme.store.model.Product

interface ProductCatalogListener {
    fun onItemPurchased(product: Product)
    fun onItemSelected(product: Product)
}