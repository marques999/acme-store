package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class Customer(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: Date,
    val updated_at: Date
) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CreditCard::class.java.classLoader),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(address1)
        parcel.writeString(address2)
        parcel.writeString(tax_number)
        parcel.writeParcelable(credit_card, flags)
        parcel.writeLong(created_at.time)
        parcel.writeLong(updated_at.time)
    }

    /**
     */
    override fun describeContents() = 0

    /**
     */
    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun newArray(size: Int) = arrayOfNulls<Customer>(size)
        override fun createFromParcel(parcel: Parcel) = Customer(parcel)
    }
}