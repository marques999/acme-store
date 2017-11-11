package org.marques999.acme.printer.orders

import org.marques999.acme.printer.common.KotlinParcelable
import org.marques999.acme.printer.common.parcelableCreator

import android.os.Parcel
import android.os.Parcelable

import org.marques999.acme.printer.views.ViewType
import org.marques999.acme.printer.products.Product

class OrderProduct(val quantity: Int, val product: Product) : ViewType, KotlinParcelable {

    /**
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Product::class.java.classLoader)
    )

    /**
     */
    override fun getViewType(): Int = ViewType.PRODUCT

    /**
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
        parcel.writeParcelable(product, flags)
    }

    /**
     */
    companion object CREATOR : Parcelable.Creator<OrderProduct> {
        override fun newArray(size: Int) = arrayOfNulls<OrderProduct>(size)
        override fun createFromParcel(parcel: Parcel) = OrderProduct(parcel)
    }
}