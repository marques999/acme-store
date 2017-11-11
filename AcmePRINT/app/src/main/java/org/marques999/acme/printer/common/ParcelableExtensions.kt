package org.marques999.acme.printer.common

import android.os.Parcel
import java.util.Date

inline fun <reified T> Parcel.readCustomArray() = arrayListOf<T>().apply {
    this@readCustomArray.readList(this, T::class.java.classLoader)
}

fun Parcel.readDate() = Date(readLong())
fun Parcel.writeDate(value: Date) = writeLong(value.time)