package org.marques999.acme.printer.views

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface ViewTypeAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

    fun inflate(group: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(group.context).inflate(layoutId, group, false)
    }
}