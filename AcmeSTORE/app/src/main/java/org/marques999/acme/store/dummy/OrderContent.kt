package org.marques999.acme.store.dummy

import java.util.ArrayList
import java.util.HashMap

object OrderContent {

    val ITEMS: MutableList<DummyItem> = ArrayList<DummyItem>()

    private val COUNT = 25
    private val ITEM_MAP: MutableMap<String, DummyItem> = HashMap<String, DummyItem>()

    init {
        (1..COUNT).forEach { addItem(createDummyItem(it)) }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int) = DummyItem(
        (position).toString(), "Item " + position, makeDetails(position)
    )

    private fun makeDetails(position: Int) = StringBuilder().apply {
        append("Details about Item: ").append(position)
        (0 until position).forEach { append("\nMore details information here.") }
    }.toString()

    class DummyItem(val id: String, val content: String, val details: String) {

        override fun toString(): String {
            return content
        }
    }
}