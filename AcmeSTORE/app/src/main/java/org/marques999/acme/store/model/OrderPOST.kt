package org.marques999.acme.store.model

class OrderPOST(val payload: List<OrderProductPOST>, val signature: String)