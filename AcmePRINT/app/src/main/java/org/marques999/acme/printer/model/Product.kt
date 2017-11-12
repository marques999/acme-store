package org.marques999.acme.printer.model

import android.os.Parcel
import android.os.Parcelable

class Product(
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(brand)
        parcel.writeDouble(price)
        parcel.writeString(barcode)
        parcel.writeString(image_uri)
        parcel.writeString(description)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun newArray(size: Int) = arrayOfNulls<Product>(size)
        override fun createFromParcel(parcel: Parcel) = Product(parcel)
    }
}