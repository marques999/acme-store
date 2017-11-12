package org.marques999.acme.printer

import android.os.Bundle
import android.net.Uri

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.printer.api.HttpErrorHandler

import android.content.Intent
import android.content.ActivityNotFoundException
import android.content.DialogInterface

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.support.v7.app.AppCompatActivity
import org.marques999.acme.printer.views.DetailsActivity

class MainActivity : AppCompatActivity() {

    /**
     */
    private val launchPlayStore = DialogInterface.OnClickListener { _, _ ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmePrinter.ZXING_URL)))
    }

    /**
     */
    private fun launchQrScanner() = startActivityForResult(
        Intent(
            AcmePrinter.ZXING_ACTIVITY
        ).putExtra(
            "SCAN_MODE", "QR_CODE_MODE"
        ), 0
    )

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivity_scan.setOnClickListener {
            try {
                launchQrScanner()
            } catch (ex: ActivityNotFoundException) {
                AcmeDialogs.buildYesNo(this, R.string.main_promptInstall, launchPlayStore).show()
            }
        }

        mainActivity_scan.isEnabled = (application as AcmePrinter).authenticate(
            this,
            Consumer {
                mainActivity_scan.isEnabled = true
                AcmeDialogs.buildOk(this, R.string.main_connectionEstablished).show()
            }
        )
    }

    /**
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data == null || requestCode != 0 || resultCode != AppCompatActivity.RESULT_OK) {
            return
        }

        val format = data.getStringExtra("SCAN_RESULT_FORMAT")

        if (format == "QR_CODE") {

            (application as AcmePrinter).api.getOrder(
                data.getStringExtra("SCAN_RESULT")
            ).subscribeOn(
                Schedulers.io()
            ).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe(
                Consumer {
                    startActivity(Intent(
                        this, DetailsActivity::class.java
                    ).putExtra(EXTRA_ORDER, it)
                    )
                },
                HttpErrorHandler(this)
            )
        } else {
            AcmeDialogs.buildOk(this, R.string.main_invalidQr, format).show()
        }
    }

    /**
     */
    companion object {
        val EXTRA_ORDER = "org.marques999.acme.printer.EXTRA_ORDER"
    }
}