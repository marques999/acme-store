package org.marques999.acme.store.view

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface ViewTypeAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

    fun inflate(view: ViewGroup, layoutId: Int): View = LayoutInflater.from(
        view.context
    ).inflate(layoutId, view, false)
}