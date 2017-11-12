package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class Order(
    val id: Int,
    val created_at: Date,
    val updated_at: Date,
    val status: Int,
    val count: Int,
    val total: Double,
    val customer: String,
    val token: String
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        Date(parcel.readLong()),
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString())

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(created_at.time)
        parcel.writeLong(updated_at.time)
        parcel.writeInt(status)
        parcel.writeInt(count)
        parcel.writeDouble(total)
        parcel.writeString(customer)
        parcel.writeString(token)
    }

    /**
     */
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        id != (other as Order).id -> false
        else -> true
    }

    /**
     */
    override fun describeContents() = 0

    override fun hashCode() = id.hashCode()
    override fun getViewType() = ViewType.ORDER_HISTORY_ORDER

    /**
     */
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun newArray(size: Int) = arrayOfNulls<Order>(size)
        override fun createFromParcel(parcel: Parcel) = Order(parcel)
    }
}