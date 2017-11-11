package org.marques999.acme.printer.orders

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.common.*
import org.marques999.acme.printer.customers.Customer
import org.marques999.acme.printer.views.ViewType

import java.util.Date

class Order(
    val token: String,
    val count: Int,
    val total: Double,
    val status: Int,
    val customer: Customer,
    val products: List<OrderProduct>,
    val created_at: Date,
    val updated_at: Date
) : Parcelable, ViewType {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readParcelable(Customer::class.java.classLoader),
        parcel.readCustomArray<OrderProduct>(),
        parcel.readDate(),
        parcel.readDate())

    /**
     */
    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.ORDER

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeInt(count)
        parcel.writeDouble(total)
        parcel.writeInt(status)
        parcel.writeParcelable(customer, flags)
        parcel.writeList(products)
        parcel.writeDate(created_at)
        parcel.writeDate(updated_at)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<Order> {
        override fun newArray(size: Int) = arrayOfNulls<Order>(size)
        override fun createFromParcel(parcel: Parcel) = Order(parcel)
    }
}