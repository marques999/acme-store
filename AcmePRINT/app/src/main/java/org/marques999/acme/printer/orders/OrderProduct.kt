package org.marques999.acme.printer.orders

import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.products.Product

class OrderProduct(val quantity: Int, val product: Product) : ViewType {
    override fun getViewType(): Int = ViewType.PRODUCT
}