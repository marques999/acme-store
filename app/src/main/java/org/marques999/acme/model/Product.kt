package org.marques999.acme.model

import org.marques999.acme.common.ViewType

import java.util.Date

data class Product(
    val id: String,
    val created: Date,
    val modified: Date,
    val name: String,
    val brand: String,
    val description: String,
    val price: Double,
    val barcode: String
) : ViewType {
    override fun getViewType(): Int = ViewType.PRODUCTS
}