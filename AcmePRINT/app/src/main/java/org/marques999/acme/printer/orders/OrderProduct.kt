package org.marques999.acme.printer.orders

import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.products.Product

class OrderProduct private constructor(
    val quantity: Int,
    val product: Product
) : java.io.Serializable, ViewType {
    override fun getViewType(): Int = ViewType.PRODUCT
}