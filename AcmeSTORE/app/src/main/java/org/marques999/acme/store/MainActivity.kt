package org.marques999.acme.store

import org.marques999.acme.store.common.AcmeDialogs
import org.marques999.acme.store.common.Authentication
import org.marques999.acme.store.common.HttpErrorHandler

import android.graphics.Color

import org.marques999.acme.store.api.AuthenticationProvider

import android.support.v7.app.AppCompatActivity

import org.marques999.acme.store.common.SessionJwt

import android.os.Bundle

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.store.products.ShoppingCartFragment

import android.support.v4.content.ContextCompat
import android.support.annotation.ColorRes
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import org.marques999.acme.store.customers.CustomerProfileFragment

import org.marques999.acme.store.dummy.OrderHistoryFragment
import org.marques999.acme.store.view.BottomNavigationAdapter
import org.marques999.acme.store.view.SwipePager


class MainActivity : AppCompatActivity() {

    /**
     */
    private lateinit var acmeInstance: AcmeStore

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        acmeInstance = application as AcmeStore
        viewPager = findViewById(R.id.viewpager)
        viewPager.setPagingEnabled(true)
        bottomNavigationAdapter = BottomNavigationAdapter(supportFragmentManager)
        bottomNavigationAdapter.addFragments(ShoppingCartFragment())
        bottomNavigationAdapter.addFragments(OrderHistoryFragment())
        bottomNavigationAdapter.addFragments(CustomerProfileFragment())
        viewPager.adapter = bottomNavigationAdapter
        bottomNavigation = findViewById(R.id.main_bottomNavigation)
        setupNavigationStyle()
        setupNavigationItems()

        bottomNavigation.setOnTabSelectedListener { position, selected ->
            onChooseTab(position, selected)
        }

        bottomNavigation.currentItem = 0
    }

    /**
     */
    private fun onChooseTab(position: Int, wasSelected: Boolean): Boolean {

        if (wasSelected) {
            return true
        } else {
            viewPager.currentItem = position
        }

        return true
    }

    /**
     */
    private lateinit var viewPager: SwipePager
    private lateinit var bottomNavigation: AHBottomNavigation
    private lateinit var bottomNavigationAdapter: BottomNavigationAdapter

    /**
     */
    private fun setupNavigationStyle() {
        bottomNavigation.defaultBackgroundColor = Color.WHITE
        bottomNavigation.isTranslucentNavigationEnabled = false
        bottomNavigation.accentColor = fetchColor(R.color.bottomtab_0)
        bottomNavigation.inactiveColor = fetchColor(R.color.bottomtab_item_resting)
        bottomNavigation.setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomtab_item_resting))
        bottomNavigation.isColored = true
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
    }

    /**
     */
    private fun setupNavigationItems() {

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_cart,
            R.drawable.ic_shopping_cart_24dp,
            R.color.colorPrimary
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_history,
            R.drawable.ic_access_time_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_profile,
            R.drawable.ic_person_24dp,
            R.color.colorPrimary
        ))
    }

    /**
     */
    private fun fetchColor(@ColorRes color: Int) = ContextCompat.getColor(
        this, color
    )

    /**
     */
    override fun onBackPressed() = if (fragmentManager.backStackEntryCount > 1) {
        fragmentManager.popBackStack()
    } else {
        finish()
    }
}