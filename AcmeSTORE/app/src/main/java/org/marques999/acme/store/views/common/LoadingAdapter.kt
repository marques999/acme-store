package org.marques999.acme.store.views.common

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R
import org.marques999.acme.store.views.ViewType
import org.marques999.acme.store.views.ViewTypeAdapter

class LoadingAdapter : ViewTypeAdapter {

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