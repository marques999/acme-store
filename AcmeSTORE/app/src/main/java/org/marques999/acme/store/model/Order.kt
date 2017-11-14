package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class Order(
    val created_at: Date,
    val customer: String,
    val token: String,
    val total: Double
) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        Date(parcel.readLong()),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble())

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(created_at.time)
        parcel.writeString(customer)
        parcel.writeString(token)
        parcel.writeDouble(total)
    }

    /**
     */
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        else -> token == (other as Order).token
    }

    /**
     */
    override fun describeContents() = 0
    override fun hashCode() = token.hashCode()

    /**
     */
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun newArray(size: Int) = arrayOfNulls<Order>(size)
        override fun createFromParcel(parcel: Parcel) = Order(parcel)
    }
}