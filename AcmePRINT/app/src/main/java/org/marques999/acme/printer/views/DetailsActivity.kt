package org.marques999.acme.printer.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_details.*

import org.marques999.acme.printer.R
import org.marques999.acme.printer.MainActivity

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        detailsActivity_recyclerView.apply {
            setHasFixedSize(true)
            clearOnScrollListeners()
            layoutManager = LinearLayoutManager(context)
            adapter = DetailsAdapter(intent.getParcelableExtra(MainActivity.EXTRA_ORDER))
        }

        OverScrollDecoratorHelper.setUpOverScroll(
            detailsActivity_recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )
    }
}