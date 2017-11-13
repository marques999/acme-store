package org.marques999.acme.store.model

import android.graphics.Bitmap

class QrCode(val bitmap: Bitmap) : ViewType {
    override fun getViewType() = ViewType.ORDER_VIEW_CODE
}