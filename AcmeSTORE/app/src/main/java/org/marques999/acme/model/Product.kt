package org.marques999.acme.model

import org.marques999.acme.common.ViewType

import java.util.Date

class Product(
    val id: String,
    val created_at: Date,
    val modified_at: Date,
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : ViewType {

    override fun getViewType(): Int = ViewType.PRODUCTS

    override fun hashCode(): Int {
        return barcode.hashCode()
    }
}