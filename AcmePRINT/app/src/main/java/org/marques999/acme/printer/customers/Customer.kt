package org.marques999.acme.printer.customers

import java.util.Date

import org.marques999.acme.printer.common.KotlinParcelable
import org.marques999.acme.printer.common.readDate
import org.marques999.acme.printer.common.writeDate

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.views.ViewType

class Customer(
    val name: String,
    val username: String,
    val address1: String,
    val address2: String,
    val country: String,
    val tax_number: String,
    val credit_card: CreditCard,
    val created_at: Date?,
    val modified_at: Date?
) : ViewType, KotlinParcelable {

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
        parcel.readDate(),
        parcel.readDate())

    /**
     */
    override fun getViewType(): Int = ViewType.CUSTOMER

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
        parcel.writeDate(modified_at)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun newArray(size: Int) = arrayOfNulls<Customer>(size)
        override fun createFromParcel(parcel: Parcel) = Customer(parcel)
    }
}