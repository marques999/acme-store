package org.marques999.acme.store.views.main

import org.marques999.acme.store.model.Product

interface MainActivityCatalogListener {
    fun onPurchase(product: Product)
}