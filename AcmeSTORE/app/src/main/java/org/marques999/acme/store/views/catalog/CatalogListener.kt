package org.marques999.acme.store.views.order

import org.marques999.acme.store.model.Product

interface CatalogListener {
    fun onItemSelected(product: Product)
}