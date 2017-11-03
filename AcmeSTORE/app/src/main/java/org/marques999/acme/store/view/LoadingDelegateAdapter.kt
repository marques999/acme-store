package org.marques999.acme.store.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import org.marques999.acme.store.R

class LoadingDelegateAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.item_loading)
    )
}