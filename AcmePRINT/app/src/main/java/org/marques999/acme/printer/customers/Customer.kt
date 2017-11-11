package org.marques999.acme.printer.customers

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

import org.marques999.acme.printer.common.readDate
import org.marques999.acme.printer.common.writeDate
import org.marques999.acme.printer.views.ViewType

class Customer(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: Date,
    val updated_at: Date
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CreditCard::class.java.classLoader),
        parcel.readDate()!!,
        parcel.readDate()!!
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(address1)
        parcel.writeString(address2)
        parcel.writeString(country)
        parcel.writeString(tax_number)
        parcel.writeParcelable(credit_card, flags)
        parcel.writeDate(created_at)
        parcel.writeDate(updated_at)
    }

    /**
     */
    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.CUSTOMER

    /**
     */
    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun newArray(size: Int) = arrayOfNulls<Customer>(size)
        override fun createFromParcel(parcel: Parcel) = Customer(parcel)
    }
}