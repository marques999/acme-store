package org.marques999.acme.printer.model

import java.util.Date

import android.os.Parcel
import android.os.Parcelable

class Order(
    val token: String,
    val count: Int,
    val total: Double,
    val status: Int,
    val customer: Customer,
    val products: List<OrderProduct>,
    val created_at: Date,
    val updated_at: Date
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readParcelable(Customer::class.java.classLoader),
        parcel.createTypedArrayList(OrderProduct.CREATOR),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    /**
     */
    override fun describeContents() = 0

    override fun getViewType(): Int = ViewType.DETAILS_ORDER

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeInt(count)
        parcel.writeDouble(total)
        parcel.writeInt(status)
        parcel.writeParcelable(customer, flags)
        parcel.writeTypedList(products)
        parcel.writeLong(created_at.time)
        parcel.writeLong(updated_at.time)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun newArray(size: Int) = arrayOfNulls<Order>(size)
        override fun createFromParcel(parcel: Parcel) = Order(parcel)
    }
}