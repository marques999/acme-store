package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class SessionJwt(val expire: Date, val token: String) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        Date(parcel.readLong()),
        parcel.readString()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(expire.time)
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