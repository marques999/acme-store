package org.marques999.acme.store.common

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class SessionJwt(val expire: java.util.Date, val token: String) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as Date,
        parcel.readString()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(expire)
        parcel.writeString(token)
    }

    /**
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<SessionJwt> {
        override fun newArray(size: Int) = arrayOfNulls<SessionJwt?>(size)
        override fun createFromParcel(parcel: Parcel) = SessionJwt(parcel)
    }
}