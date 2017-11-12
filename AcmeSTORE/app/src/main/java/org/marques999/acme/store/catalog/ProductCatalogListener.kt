package org.marques999.acme.store.catalog

import org.marques999.acme.store.model.Product

interface ProductCatalogListener {
    fun onPurchase(product: String)
}