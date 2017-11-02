package org.marques999.acme.printer

import org.marques999.acme.printer.api.AcmeProvider

class AcmePrinter : android.app.Application() {
    lateinit var acmeApi: AcmeProvider
}