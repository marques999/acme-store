package org.marques999.acme.store.model

class OrderPOST(private val payload: List<OrderProductPOST>, private val signature: String)