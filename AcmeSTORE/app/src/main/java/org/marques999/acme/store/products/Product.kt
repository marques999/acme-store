package org.marques999.acme.store.products

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class Product private constructor(
    val id: String,
    val created_at: Date?,
    val modified_at: Date?,
    val name: String,
    val brand: String,
    val price: Double,
    val barcode: String,
    var image_uri: String,
    val description: String
) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readSerializable() as Date?,
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    /**
     */
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        else -> id == (other as Product).id
    }

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeSerializable(created_at)
        parcel.writeSerializable(modified_at)
        parcel.writeString(name)
        parcel.writeString(brand)
        parcel.writeDouble(price)
        parcel.writeString(barcode)
        parcel.writeString(image_uri)
        parcel.writeString(description)
    }

    /**
     */
    override fun describeContents() = 0

    /**
     */
    override fun hashCode(): Int = barcode.hashCode()

    /**
     */
    companion object CREATOR : Parcelable.Creator<Product> {
        override fun newArray(size: Int) = arrayOfNulls<Product>(size)
        override fun createFromParcel(parcel: Parcel) = Product(parcel)
    }
}