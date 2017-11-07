package org.marques999.acme.store.history

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.marques999.acme.store.R

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class OrderHistoryFragment : Fragment() {

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_history, container, false).apply {
        if (this is RecyclerView) {
            layoutManager = LinearLayoutManager(getContext())
            adapter = OrderAdapter(OrderContent.orders)
        }
    }
}