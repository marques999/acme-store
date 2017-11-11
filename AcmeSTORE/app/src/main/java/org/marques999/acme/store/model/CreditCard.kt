package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

import org.marques999.acme.store.common.readDate
import org.marques999.acme.store.common.writeDate

class CreditCard(
    val type: String,
    val number: String,
    val validity: Date
) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDate()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(number)
        parcel.writeDate(validity)
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