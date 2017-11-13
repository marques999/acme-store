package org.marques999.acme.store.model

class CustomerCart {

    /**
     */
    private val shoppingCart = HashMap<String, OrderProduct>()

    /**
     */
    fun count() = shoppingCart.size
    fun checkout() = shoppingCart.clear()
    fun getProducts() = shoppingCart.values
    fun notEmpty() = shoppingCart.isNotEmpty()
    fun delete(barcode: String) = shoppingCart.remove(barcode)

    /**
     */
    operator fun get(barcode: String) = shoppingCart[barcode]

    /**
     */
    fun convertJson() = shoppingCart.values.map {
        OrderProductPOST(it.quantity, it.product.barcode)
    }

    /**
     */
    fun calculate(): Pair<Int, Double> = if (shoppingCart.isEmpty()) {
        0 to 0.0
    } else {
        shoppingCart.values.map {
            it.quantity to it.product.price * it.quantity
        }.reduce { acc, pair ->
            acc.first + pair.first to acc.second + pair.second
        }
    }

    /**
     */
    fun insert(product: Product) = (shoppingCart[product.barcode] ?: OrderProduct(
        0, product
    )).apply {
        quantity++
        shoppingCart.put(product.barcode, this)
    }

    /**
     */
    fun update(barcode: String, delta: Int): OrderProduct? {

        var quantityChanged = false

        shoppingCart[barcode]?.apply {

            if (delta != 0 && (delta > 0 && quantity < 99) || (delta < 0 && quantity > 1)) {
                quantity += delta
                quantityChanged = true
            }
        }

        return if (quantityChanged) {
            shoppingCart[barcode]
        } else {
            null
        }
    }
}