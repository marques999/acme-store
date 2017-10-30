package org.marques999.acme.model

class CustomerCart {

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

    fun toJSON(): List<OrderProductPOST> {
        return products.map { OrderProductPOST(it.value, it.key.barcode) }
    }
}