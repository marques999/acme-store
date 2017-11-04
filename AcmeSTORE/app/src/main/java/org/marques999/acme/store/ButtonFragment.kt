package org.marques999.acme.store

import android.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.os.Bundle

class ButtonFragment : Fragment() {

    /**
     */
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater!!.inflate(R.layout.fragment_button, container, false)
}