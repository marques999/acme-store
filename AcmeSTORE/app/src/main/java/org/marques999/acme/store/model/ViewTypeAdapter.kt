package org.marques999.acme.store.model

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import android.support.v7.widget.RecyclerView

interface ViewTypeAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(
    context
).inflate(layoutId, this, false)