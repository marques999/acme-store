package org.marques999.acme.store.catalog

interface ProductCatalogListener {
    fun onPurchase(product: String)
}