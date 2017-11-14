package org.marques999.acme.store

import android.content.Context
import android.graphics.Bitmap

import java.util.Date
import java.util.Locale
import java.util.Currency

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

import java.text.DateFormat
import java.text.NumberFormat

object AcmeUtils {

    /**
     */
    private val applicationLocale = Locale("pt", "PT")

    /**
     */
    private val dateFormat = DateFormat.getDateInstance(
        DateFormat.MEDIUM, applicationLocale
    )

    /**
     */
    private val dateTimeFormat = DateFormat.getDateTimeInstance(
        DateFormat.MEDIUM, DateFormat.MEDIUM, applicationLocale
    )

    /**
     */
    private val currencyFormat = NumberFormat.getCurrencyInstance(
        Locale.getDefault()
    ).apply {
        currency = Currency.getInstance("EUR")
    }

    /**
     */
    fun encodeQrCode(context: Context, qrCode: String): Bitmap {

        val qrDimensions = context.resources.getDimension(R.dimen.width_qrCode).toInt()

        val bitMatrix = MultiFormatWriter().encode(
            qrCode, BarcodeFormat.QR_CODE, qrDimensions, qrDimensions, null
        )

        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        val black = AcmeStore.fetchColor(context, android.R.color.black)
        val white = AcmeStore.fetchColor(context, android.R.color.white)

        (0 until height).forEach { y ->

            val offset = y * width

            (0 until width).forEach { x ->
                pixels[offset + x] = if (bitMatrix.get(x, y)) black else white
            }
        }

        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, width, 0, 0, width, height)
        }
    }

    /**
     */
    fun formatDate(value: Date): String = dateFormat.format(value)
    fun formatDateTime(value: Date): String = dateTimeFormat.format(value)
    fun formatCurrency(value: Double): String = currencyFormat.format(value)
}