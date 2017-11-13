package org.marques999.acme.printer.model

import java.util.Date

import android.os.Parcel
import android.os.Parcelable

class Customer(
    val name: String,
    private val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    private val created_at: Date,
    private val updated_at: Date
) : ViewType, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CreditCard::class.java.classLoader),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(address1)
        parcel.writeString(address2)
        parcel.writeString(country)
        parcel.writeString(tax_number)
        parcel.writeParcelable(credit_card, flags)
        parcel.writeLong(created_at.time)
        parcel.writeLong(updated_at.time)
    }

    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.DETAILS_CUSTOMER

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun newArray(size: Int) = arrayOfNulls<Customer>(size)
        override fun createFromParcel(parcel: Parcel) = Customer(parcel)
    }
}