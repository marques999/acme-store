package org.marques999.acme.store.common

import android.os.Parcel

fun Parcel.readDate() = java.util.Date(readLong())
fun Parcel.writeDate(value: java.util.Date) = writeLong(value.time)