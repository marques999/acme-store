package org.marques999.acme.store.views.common

import android.util.SparseArray

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import android.view.ViewGroup

class BottomNavigationAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    /**
     */
    private val fragments = ArrayList<Fragment>()
    private val registeredFragments = SparseArray<Fragment>()

    /**
     */
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        return (super.instantiateItem(container, position) as Fragment).apply {
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
    fun addFragments(fragment: Fragment) = fragments.add(fragment)
    fun getFragment(position: Int) = registeredFragments.get(position)
}