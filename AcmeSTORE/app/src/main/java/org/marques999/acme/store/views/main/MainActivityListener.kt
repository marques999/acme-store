package org.marques999.acme.store.views.main

interface MainActivityListener {
    fun onUpdateBadge(view: Int, value: Int)
    fun onNotify(messageId: MainActivityMessage, value: Any)
}