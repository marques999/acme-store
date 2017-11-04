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
import org.marques999.acme.printer.common.Session
import org.marques999.acme.printer.common.SessionJwt

class MainActivity : Activity() {

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
        main_scan.setOnClickListener { scanQrCode() }

        if (main_scan.isEnabled && savedInstanceState != null) {
            return
        }

        main_scan.isEnabled = false
        authenticate("admin", "admin")
    }

    /**
     */
    private fun authenticate(username: String, password: String) = AuthenticationProvider().login(
        username, password
    ).observeOn(
        AndroidSchedulers.mainThread()
    ).subscribeOn(
        Schedulers.io()
    ).subscribe(
        onLogin(username), HttpErrorHandler(this)
    )

    /**
     */
    private fun onLogin(username: String) = Consumer<SessionJwt> {
        AcmeDialogs.buildOk(this, R.string.main_connected).show()
        (application as AcmePrinter).acmeApi = AcmeProvider(Session(it, username))
        main_scan.isEnabled = true
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

        if (data == null || requestCode != 0 || resultCode != Activity.RESULT_OK) {
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