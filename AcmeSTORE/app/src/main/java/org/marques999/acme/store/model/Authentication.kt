package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

class Authentication(val username: String, val password: String) : Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
    }

    /**
     */
    override fun describeContents() = 0

    /**
     */
    companion object CREATOR : Parcelable.Creator<Authentication> {
        override fun newArray(size: Int) = arrayOfNulls<Authentication>(size)
        override fun createFromParcel(parcel: Parcel) = Authentication(parcel)
    }
}