package org.marques999.acme.printer.customers

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.common.KotlinParcelable
import org.marques999.acme.printer.common.readDate
import org.marques999.acme.printer.common.writeDate
import org.marques999.acme.printer.views.ViewType

import java.util.Date

class CreditCard(
    val type: String,
    val number: String,
    val validity: Date
) : ViewType, KotlinParcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDate()!!
    )

    /**
     */
    override fun getViewType(): Int = ViewType.CREDIT_CARD

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(number)
        parcel.writeDate(validity)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<CreditCard> {
        override fun newArray(size: Int) = arrayOfNulls<CreditCard>(size)
        override fun createFromParcel(parcel: Parcel) = CreditCard(parcel)
    }
}