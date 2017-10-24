package org.marques999.acme.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewTypeAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}