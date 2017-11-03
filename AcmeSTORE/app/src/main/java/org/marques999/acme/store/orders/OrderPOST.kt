package org.marques999.acme.store.orders

class OrderPOST(
    private val payload: List<OrderProductPOST>,
    private val signature: String
)