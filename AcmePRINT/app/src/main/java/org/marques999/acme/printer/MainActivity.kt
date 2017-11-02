package org.marques999.acme.printer

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.net.Uri

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.printer.api.AcmeProvider
import org.marques999.acme.printer.api.AuthenticationProvider

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.printer.common.AcmeDialogs
import org.marques999.acme.printer.common.HttpErrorHandler
import org.marques999.acme.printer.common.Token

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (scan1.isEnabled && savedInstanceState != null) {
            return
        }

        scan1.isEnabled = false
        scan1.setOnClickListener { scanQrCode() }
        authenticateUser()
    }

    private val onLogin = Consumer<Token> {
        AcmeDialogs.showOk(this@MainActivity, ACTIVITY_NAME, "Authorized: ${it.token}")
        (application as AcmePrinter).acmeApi = AcmeProvider(it)
        scan1.isEnabled = true
    }

    private fun authenticateUser() = AuthenticationProvider()
        .login("admin", "admin")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(onLogin, HttpErrorHandler(this))

    private fun scanQrCode() {

        try {
            startActivityForResult(
                Intent(ACTION_SCAN).putExtra("SCAN_MODE", "QR_CODE_MODE"), 0
            )
        } catch (ex: ActivityNotFoundException) {
            AcmeDialogs.showYesNo(
                this,
                ACTIVITY_NAME,
                "Launch Play Store and install a QR code scanner activity?",
                DialogInterface.OnClickListener { _, _ ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.ZXING_URL)))
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data == null || requestCode != 0 || resultCode != Activity.RESULT_OK) {
            return
        }

        val format = data.getStringExtra("SCAN_RESULT_FORMAT")

        if (format == FORMAT_QR_CODE) {

            startActivity(
                Intent(this, DetailsActivity::class.java).putExtra(
                    EXTRA_TOKEN, data.getStringExtra("SCAN_RESULT")
                )
            )
        } else {
            AcmeDialogs.showOk(
                this,
                ACTIVITY_NAME,
                "The code you scanned was recognized as $format, expected \"$FORMAT_QR_CODE\"."
            )
        }
    }

    companion object {

        private val FORMAT_QR_CODE = "QR_CODE"
        private val ACTIVITY_NAME = "Barcode Scanner"
        private val ACTION_SCAN = "com.google.zxing.client.android.SCAN"
        private val ZXING_URL = "market://details?id=com.google.zxing.client.android"

        val EXTRA_TOKEN = "org.marques999.acme.printer.TOKEN"
    }
}