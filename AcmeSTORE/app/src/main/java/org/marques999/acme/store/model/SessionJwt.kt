package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

import org.marques999.acme.store.common.readDate
import org.marques999.acme.store.common.writeDate

class SessionJwt(val expire: Date, val token: String) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readDate(),
        parcel.readString()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDate(expire)
        parcel.writeString(token)
    }

    /**
     */
    override fun describeContents() = 0

    /**
     */
    companion object CREATOR : Parcelable.Creator<SessionJwt> {
        override fun newArray(size: Int) = arrayOfNulls<SessionJwt>(size)
        override fun createFromParcel(parcel: Parcel) = SessionJwt(parcel)
    }
}