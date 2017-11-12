package org.marques999.acme.printer.model

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import android.support.v7.widget.RecyclerView

interface ViewTypeAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

    fun inflate(group: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(group.context).inflate(layoutId, group, false)
    }
}