package org.marques999.acme.store.view

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R

class RecyclerLoadingAdapter : ViewTypeAdapter {

    /**
     */
    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    /**
     */
    inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.item_loading)
    )
}