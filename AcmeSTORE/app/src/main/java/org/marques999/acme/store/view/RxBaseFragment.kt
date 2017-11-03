package org.marques999.acme.store.view

import android.app.Fragment

import io.reactivex.disposables.CompositeDisposable

open class RxBaseFragment : Fragment() {

    private var subscriptions = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }
}