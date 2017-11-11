package org.marques999.acme.printer.orders

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.products.Product

class OrderProduct(val quantity: Int, val product: Product) : ViewType, Parcelable {

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
    override fun describeContents() = 0
    override fun getViewType(): Int = ViewType.PRODUCT

    /**
     */
    companion object CREATOR : Parcelable.Creator<OrderProduct> {
        override fun newArray(size: Int) = arrayOfNulls<OrderProduct>(size)
        override fun createFromParcel(parcel: Parcel) = OrderProduct(parcel)
    }
}