package org.marques999.acme.printer

import android.net.Uri
import android.os.Bundle
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import android.content.Intent
import android.content.ActivityNotFoundException

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.printer.api.HttpErrorHandler
import org.marques999.acme.printer.views.DetailsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_scan.setOnClickListener {

            try {
                startActivityForResult(Intent(
                    AcmePrinter.ZXING_ACTIVITY
                ).putExtra(
                    "SCAN_MODE", "QR_CODE_MODE"
                ), 0)
            } catch (ex: ActivityNotFoundException) {
                AlertDialog.Builder(this).setTitle(
                    R.string.activity_main
                ).setMessage(
                    R.string.main_install
                ).setNegativeButton(
                    android.R.string.no, null
                ).setPositiveButton(android.R.string.yes, { _, _ ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AcmePrinter.ZXING_URL)))
                }).show()
            }
        }

        main_scan.isEnabled = (application as AcmePrinter).authenticate(
            this, Consumer {
            main_scan.isEnabled = true
            AcmeDialogs.buildOk(this, R.string.main_connected).show()
        })
    }

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
                    ).putExtra(EXTRA_ORDER, it))
                }, HttpErrorHandler(this)
            )
        } else {
            AcmeDialogs.buildOk(this, R.string.main_invalidQr, format).show()
        }
    }

    companion object {
        val EXTRA_ORDER = "org.marques999.acme.printer.EXTRA_ORDER"
    }
}