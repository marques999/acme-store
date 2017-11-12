package org.marques999.acme.store.views.common

import android.view.MenuItem
import android.support.v7.app.AppCompatActivity

abstract class BackButtonActivity : AppCompatActivity() {

    /**
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {

        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}