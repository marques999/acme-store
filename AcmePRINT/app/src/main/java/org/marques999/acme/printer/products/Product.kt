package org.marques999.acme.printer.products

class Product private constructor(
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : java.io.Serializable