package org.marques999.acme.printer.model

import java.util.Date

import android.os.Parcel
import android.os.Parcelable

class CreditCard(
    val type: String,
    val number: String,
    val validity: Date
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        Date(parcel.readLong())
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(number)
        parcel.writeLong(validity.time)
    }

    /**
     */
    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.DETAILS_CREDIT_CARD

    /**
     */
    companion object CREATOR : Parcelable.Creator<CreditCard> {
        override fun newArray(size: Int) = arrayOfNulls<CreditCard>(size)
        override fun createFromParcel(parcel: Parcel) = CreditCard(parcel)
    }
}