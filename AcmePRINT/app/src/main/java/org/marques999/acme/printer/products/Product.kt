package org.marques999.acme.printer.products

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.common.KotlinParcelable
import org.marques999.acme.printer.common.parcelableCreator
import org.marques999.acme.printer.orders.Order

class Product(
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : KotlinParcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(brand)
        parcel.writeDouble(price)
        parcel.writeString(barcode)
        parcel.writeString(image_uri)
        parcel.writeString(description)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<Product> {
        override fun newArray(size: Int) = arrayOfNulls<Product>(size)
        override fun createFromParcel(parcel: Parcel) = Product(parcel)
    }
}