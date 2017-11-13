package org.marques999.acme.printer.model

import android.os.Parcel
import android.os.Parcelable

class Customer(
    val name: String,
    val address1: String,
    val address2: String,
    val tax_number: String,
    val credit_card: CreditCard
) : ViewType, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CreditCard::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address1)
        parcel.writeString(address2)
        parcel.writeString(tax_number)
        parcel.writeParcelable(credit_card, flags)
    }

    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.DETAILS_CUSTOMER

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun newArray(size: Int) = arrayOfNulls<Customer>(size)
        override fun createFromParcel(parcel: Parcel) = Customer(parcel)
    }
}