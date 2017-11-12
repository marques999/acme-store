package org.marques999.acme.store.catalog

import android.os.Bundle
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import org.marques999.acme.store.R

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class ProductCatalogFragment : Fragment() {

    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dummy_list, container, false)

        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(view.getContext())
            view.adapter = ProductCatalogAdapter(ProductCatalogContent.products, mListener)
        }

        return view
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ProductCatalogContent.DummyItem)
    }
}