package org.marques999.acme.store.orders

import org.marques999.acme.store.view.ViewType

class Product private constructor(
    val id: String,
    val created_at: java.util.Date,
    val modified_at: java.util.Date,
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : ViewType {

    override fun hashCode(): Int = barcode.hashCode()
    override fun getViewType(): Int = ViewType.PRODUCTS

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        else -> id == (other as Product).id
    }
}