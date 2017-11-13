package org.marques999.acme.printer.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import org.marques999.acme.printer.R
import org.marques999.acme.printer.MainActivity

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        details_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            adapter = DetailsAdapter(intent.getParcelableExtra(MainActivity.EXTRA_ORDER))
        }
    }
}