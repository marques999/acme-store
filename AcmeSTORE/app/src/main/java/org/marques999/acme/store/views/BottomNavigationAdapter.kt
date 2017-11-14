package org.marques999.acme.store.views

import android.view.ViewGroup
import android.util.SparseArray
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import org.marques999.acme.store.views.main.MainActivityFragment

class BottomNavigationAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    /**
     */
    private val fragments = ArrayList<MainActivityFragment>()
    private val registeredFragments = SparseArray<MainActivityFragment>()

    /**
     */
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        return (super.instantiateItem(container, position) as MainActivityFragment).apply {
            registeredFragments.put(position, this)
        }
    }

    /**
     */
    override fun destroyItem(container: ViewGroup?, position: Int, item: Any?) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, item)
    }

    /**
     */
    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int) = fragments[position]

    /**
     */
    fun addFragments(fragment: MainActivityFragment) = fragments.add(fragment)
    fun getFragment(position: Int): MainActivityFragment = registeredFragments[position]

    /**
     */
    companion object {
        val CART = 0
        val CATALOG = 1
        val HISTORY = 2
    }
}