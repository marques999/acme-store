package org.marques999.acme.store.customers

import android.graphics.Bitmap
import android.os.Bundle

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeStore

import kotlinx.android.synthetic.main.fragment_profile.*

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

class CustomerProfileFragment : Fragment() {

    /**
     */
    private lateinit var customer: CustomerJSON

    /**
     */
    private val qrContent = String(
        "8aqHkFw8".toByteArray(),
        charset("ISO-8859-1")
    )

    /**
     */
    private val encodeQrBitmap = Runnable {

        var qrException = ""

        try {

            val qrCode = encodeQrCode(qrContent)

            activity.runOnUiThread {
                qrCode_imageView.setImageBitmap(qrCode)
            }
        } catch (e: WriterException) {
            qrException += "\n" + e.message
        }

        if (qrException.isNotBlank()) {

            activity.runOnUiThread {
                qrCode_textView.text = qrException
            }
        }
    }

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        R.layout.fragment_profile, container, false
    )

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread { encodeQrBitmap }.start()
        customer = (activity.application as AcmeStore).acmeApi.getCustomer()
    }

    /**
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customerProfile_name.text = customer.name
        customerProfile_nif.text = customer.tax_number
        customerProfile_address1.text = customer.address1
        customerProfile_address2.text = customer.address2
    }

    /**
     */
    @Throws(WriterException::class)
    private fun encodeQrCode(qrCode: String?): Bitmap? {

        val bitMatrix: BitMatrix
        val qrDimensions = resources.getDimension(R.dimen.qrCode_imageView).toInt()

        try {
            bitMatrix = MultiFormatWriter().encode(
                qrCode, BarcodeFormat.QR_CODE, qrDimensions, qrDimensions, null
            )
        } catch (iae: IllegalArgumentException) {
            return null
        }

        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)

        (0 until height).forEach { y ->

            val offset = y * width

            (0 until width).forEach { x ->

                pixels[offset + x] = if (bitMatrix.get(x, y)) {
                    ContextCompat.getColor(context, R.color.colorPrimary)
                } else {
                    ContextCompat.getColor(context, android.R.color.white)
                }
            }
        }

        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, width, 0, 0, width, height)
        }
    }
}