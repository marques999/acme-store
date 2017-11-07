package org.marques999.acme.printer

import io.reactivex.functions.Consumer

import android.os.Bundle
import android.net.Uri
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

import android.content.Intent
import android.content.ActivityNotFoundException
import android.content.DialogInterface

import org.marques999.acme.printer.common.AcmeDialogs

class MainActivity : AppCompatActivity() {

    /**
     */
    private val launchPlayStore = DialogInterface.OnClickListener { _, _ ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmePrinter.ZXING_URL)))
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivity_scan.setOnClickListener { scanQrCode() }

        mainActivity_scan.isEnabled = (application as AcmePrinter).authenticate(
            "admin",
            "admin",
            Consumer {
                mainActivity_scan.isEnabled = true
                AcmeDialogs.buildOk(this, R.string.main_connectionEstablished).show()
            }
        )
    }

    /**
     */
    private fun launchQrScanner() = startActivityForResult(Intent(
        AcmePrinter.ZXING_ACTIVITY
    ).putExtra(
        "SCAN_MODE", "QR_CODE_MODE"
    ), 0)

    /**
     */
    private fun scanQrCode() = try {
        launchQrScanner()
    } catch (ex: ActivityNotFoundException) {
        AcmeDialogs.buildYesNo(this, R.string.main_promptInstall, launchPlayStore).show()
    }

    /**
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data == null || requestCode != 0 || resultCode != AppCompatActivity.RESULT_OK) {
            return
        }

        val format = data.getStringExtra("SCAN_RESULT_FORMAT")

        if (format == "QR_CODE") {

            startActivity(Intent(
                this, DetailsActivity::class.java
            ).putExtra(
                AcmePrinter.EXTRA_TOKEN, data.getStringExtra("SCAN_RESULT")
            ))
        } else {
            AcmeDialogs.buildOk(this, R.string.main_invalidQr, format).show()
        }
    }
}