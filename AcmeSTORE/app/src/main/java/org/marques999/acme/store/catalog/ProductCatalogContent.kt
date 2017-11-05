package org.marques999.acme.store.catalog

import java.util.ArrayList
import java.util.HashMap

object ProductCatalogContent {

    private val productsLength = 25
    private val productsHash = HashMap<String, DummyItem>()
    val products: MutableList<DummyItem> = ArrayList()

    init {
        (1..productsLength).forEach {
            insertProduct(DummyItem(
                it.toString(), "Item " + it,
                makeDetails(it)
            ))
        }
    }

    private fun insertProduct(item: DummyItem) {
        products.add(item)
        productsHash.put(item.id, item)
    }

    private fun makeDetails(position: Int) = StringBuilder().apply {

        append("Details about Item: ")
        append(position)

        (0 until position).forEach {
            append("\nMore details information here.")
        }
    }.toString()

    class DummyItem(val id: String, val content: String, val details: String) {

        override fun toString(): String {
            return content
        }
    }
}