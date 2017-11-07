package org.marques999.acme.store.orders

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

import kotlinx.android.synthetic.main.activity_confirm.*
import org.marques999.acme.store.AcmeStore

import org.marques999.acme.store.R

class OrderConfirmActivity : AppCompatActivity() {

    /**
     */
    private val qrContent = String(
        "lAWHVikY".toByteArray(),
        charset("ISO-8859-1")
    )

    /**
     */
    private val encodeQrBitmap = Thread {

        var qrException = ""

        try {

            val qrCode = encodeQrCode(qrContent)

            runOnUiThread {
                qrCode_imageView.setImageBitmap(qrCode)
            }
        } catch (exception: WriterException) {
            qrException += "\n" + exception.message
        }

        if (qrException.isNotBlank()) {
            runOnUiThread { qrCode_textView.text = qrException }
        }
    }

    /**
     */
    @Throws(WriterException::class)
    private fun encodeQrCode(qrCode: String?): Bitmap {

        val qrDimensions = resources.getDimension(R.dimen.qrCode_imageView).toInt()

        val bitMatrix = MultiFormatWriter().encode(
            qrCode, BarcodeFormat.QR_CODE, qrDimensions, qrDimensions, null
        )

        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        val black = AcmeStore.fetchColor(this, android.R.color.black)
        val white = AcmeStore.fetchColor(this, android.R.color.white)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        encodeQrBitmap.start()
    }
}