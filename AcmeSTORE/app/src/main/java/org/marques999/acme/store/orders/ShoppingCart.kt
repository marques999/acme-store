package org.marques999.acme.store.orders

class ShoppingCart {

    private val products = HashMap<Product, Int>()

    fun insert(product: Product, quantity: Int) {

        val previous = products[product]

        if (previous != null) {
            products.put(product, previous + quantity)
        } else {
            products.put(product, quantity)
        }
    }

    fun remove(product: Product) {
        products.remove(product)
    }

    fun toJSON() = products.map {
        OrderProductPOST(it.value, it.key.barcode)
    }
}