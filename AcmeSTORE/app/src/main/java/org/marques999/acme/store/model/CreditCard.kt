package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class CreditCard(
    private val type: String,
    private val number: String,
    private val validity: Date
) : Parcelable {

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

    /**
     */
    companion object CREATOR : Parcelable.Creator<CreditCard> {
        override fun newArray(size: Int) = arrayOfNulls<CreditCard>(size)
        override fun createFromParcel(parcel: Parcel) = CreditCard(parcel)
    }
}