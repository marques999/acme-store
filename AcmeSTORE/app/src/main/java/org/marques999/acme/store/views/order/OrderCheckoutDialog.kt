package org.marques999.acme.store.views.order

import android.app.Dialog
import android.app.AlertDialog

import com.google.zxing.WriterException

import android.os.Bundle
import android.widget.ImageView
import android.view.LayoutInflater
import android.support.v4.app.DialogFragment

import org.marques999.acme.store.R
import org.marques999.acme.store.AcmeDialogs
import org.marques999.acme.store.AcmeUtils

class OrderCheckoutDialog : DialogFragment() {

    /**
     */
    private lateinit var qrCode: ImageView

    /**
     */
    private fun encodeQrBitmap(qrContent: String) = Thread {

        try {

            val qrBitmap = AcmeUtils.encodeQrCode(context, String(
                qrContent.toByteArray(),
                charset("ISO-8859-1")
            ))

            activity.runOnUiThread {
                qrCode.setImageBitmap(qrBitmap)
            }
        } catch (exception: WriterException) {

            activity.runOnUiThread {
                AcmeDialogs.buildOk(context, exception, exception.localizedMessage)
            }
        }
    }

    /**
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(
        activity
    ).setView(
        LayoutInflater.from(context).inflate(R.layout.dialog_code, null)
    ).create()

    /**
     */
    override fun onStart() {
        super.onStart()
        qrCode = dialog.findViewById(R.id.qrCode)
        arguments.getString(ORDER_TOKEN)?.let { encodeQrBitmap(it).start() }
    }

    /**
     */
    companion object {

        /**
         */
        fun newInstance(qrCode: String): OrderCheckoutDialog = OrderCheckoutDialog().apply {
            arguments = Bundle().apply { putString(ORDER_TOKEN, qrCode) }
        }

        /**
         */
        private val ORDER_TOKEN = "org.marques999.acme.bundles.ORDER_TOKEN"
    }
}