package org.marques999.acme.store.products

import android.view.ViewGroup

import org.marques999.acme.store.R
import org.marques999.acme.store.view.ViewType
import org.marques999.acme.store.view.ViewTypeAdapter

import android.support.v7.widget.RecyclerView

class ProductsLoadingAdapter : ViewTypeAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        inflate(parent, R.layout.item_loading)
    )
}