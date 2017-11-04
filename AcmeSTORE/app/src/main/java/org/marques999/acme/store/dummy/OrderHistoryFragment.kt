package org.marques999.acme.store.dummy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R

class OrderHistoryFragment : Fragment() {

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_shopping_history, container, false)

        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(view.getContext())
            view.adapter = OrderAdapter(OrderContent.ITEMS)
        }

        return view
    }
}