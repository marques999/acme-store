package org.marques999.acme.store.model

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.store.views.ViewType

class OrderProduct(var quantity: Int, val product: Product) : ViewType, Parcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Product::class.java.classLoader)
    )

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
        parcel.writeParcelable(product, flags)
    }

    /**
     */
    override fun describeContents(): Int = 0
    override fun getViewType() = ViewType.ORDER_VIEW_PRODUCT

    /**
     */
    companion object CREATOR : Parcelable.Creator<OrderProduct> {
        override fun newArray(size: Int) = arrayOfNulls<OrderProduct>(size)
        override fun createFromParcel(parcel: Parcel) = OrderProduct(parcel)
    }
}