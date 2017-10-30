package org.marques999.acme.model

data class OrderPOST(private val payload: List<OrderProductPOST>, private val signature: String)