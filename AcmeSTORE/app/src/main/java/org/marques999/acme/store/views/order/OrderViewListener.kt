package org.marques999.acme.store.views.order

import org.marques999.acme.store.model.OrderProduct

interface OrderViewListener {
    fun onViewProduct(orderProduct: OrderProduct)
}