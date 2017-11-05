package org.marques999.acme.store.history

import java.util.ArrayList
import java.util.HashMap

object OrderContent {

    /**
     */
    val orders = ArrayList<DummyItem>()

    /**
     */
    private val ordersHash = HashMap<String, DummyItem>()

    /**
     */
    data class DummyItem(val id: String, val content: String)

    /**
     */
    init {
        (1..25).forEach { addItem(DummyItem(it.toString(), "Item $it")) }
    }

    /**
     */
    private fun addItem(item: DummyItem) {
        orders.add(item)
        ordersHash.put(item.id, item)
    }
}