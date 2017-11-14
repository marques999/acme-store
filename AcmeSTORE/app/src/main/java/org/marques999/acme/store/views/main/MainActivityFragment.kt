package org.marques999.acme.store.views.main

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class MainActivityFragment(private val layoutId: Int) : Fragment(), MainActivityMenuListener {

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(
        layoutId, container, false
    )
}