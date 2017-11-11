package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable
import org.marques999.acme.store.common.readDate
import org.marques999.acme.store.common.writeDate
import org.marques999.acme.store.views.ViewType
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
) : Parcelable, ViewType {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDate(),
        parcel.readDate(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString())

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDate(created_at)
        parcel.writeDate(updated_at)
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
    override fun getViewType() = ViewType.ORDERS

    /**
     */
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}