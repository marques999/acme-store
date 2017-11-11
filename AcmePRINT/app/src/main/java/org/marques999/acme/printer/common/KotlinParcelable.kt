package org.marques999.acme.printer.common

import android.os.Parcel
import java.util.Date

inline fun <T> Parcel.readNullable(reader: () -> T) =
    if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }
}

fun Parcel.readDate() =
    readNullable { Date(readLong()) }

fun Parcel.writeDate(value: Date?) =
    writeNullable(value) { writeLong(it.time) }

inline fun <reified T> Parcel.readCustomArray() = arrayListOf<T>().apply {
    this@readCustomArray.readList(this, T::class.java.classLoader)
}