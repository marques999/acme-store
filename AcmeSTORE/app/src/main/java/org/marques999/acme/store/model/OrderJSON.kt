package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Date

class OrderJSON(
    val count: Int,
    val created_at: Date,
    val customer: Customer,
    val products: List<OrderProduct>,
    val token: String,
    val total: Double,
    val updated_at: Date
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        Date(parcel.readLong()),
        parcel.readParcelable(Customer::class.java.classLoader),
        parcel.createTypedArrayList(OrderProduct),
        parcel.readString(),
        parcel.readDouble(),
        Date(parcel.readLong())
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeLong(created_at.time)
        parcel.writeParcelable(customer, flags)
        parcel.writeTypedList(products)
        parcel.writeString(token)
        parcel.writeDouble(total)
        parcel.writeLong(updated_at.time)
    }

    /**
     */
    override fun describeContents() = 0
    override fun getViewType() = ViewType.ORDER_VIEW_ORDER

    /**
     */
    companion object CREATOR : Parcelable.Creator<OrderJSON> {
        override fun createFromParcel(parcel: Parcel) = OrderJSON(parcel)
        override fun newArray(size: Int) = arrayOfNulls<OrderJSON>(size)
    }
}