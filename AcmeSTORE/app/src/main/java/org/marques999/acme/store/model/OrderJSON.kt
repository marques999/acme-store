package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.store.common.readDate
import org.marques999.acme.store.common.writeDate
import org.marques999.acme.store.views.ViewType

class OrderJSON(
    val count: Int,
    val created_at: java.util.Date,
    val customer: Customer,
    val products: List<OrderProduct>,
    val status: Int,
    val token: String,
    val total: Double,
    val updated_at: java.util.Date
) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDate(),
        parcel.readParcelable(Customer::class.java.classLoader),
        parcel.createTypedArrayList(OrderProduct),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDate()
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeDate(created_at)
        parcel.writeParcelable(customer, flags)
        parcel.writeTypedList(products)
        parcel.writeInt(status)
        parcel.writeString(token)
        parcel.writeDouble(total)
        parcel.writeDate(updated_at)
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