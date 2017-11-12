package org.marques999.acme.store.views.register

interface RegisterStepTwoListener {
    fun previousPage()
    fun submitCustomer(parameters: Map<String, Any>)
}