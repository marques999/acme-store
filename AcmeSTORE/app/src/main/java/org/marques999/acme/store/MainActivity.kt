package org.marques999.acme.store

import android.content.Intent
import android.os.Bundle

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

import android.graphics.Color

import org.marques999.acme.store.customers.CustomerProfileFragment
import org.marques999.acme.store.history.OrderHistoryFragment
import org.marques999.acme.store.products.ShoppingCartFragment
import org.marques999.acme.store.view.BottomNavigationAdapter

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import org.marques999.acme.store.catalog.ProductCatalogFragment
import org.marques999.acme.store.common.SessionJwt

class MainActivity : AppCompatActivity() {

    /**
     */
    private lateinit var acmeInstance: AcmeStore

    /**
     */
    private val barTitles = arrayOf(
        R.string.actionBar_cart,
        R.string.actionBar_catalog,
        R.string.actionBar_history,
        R.string.actionBar_catalog
    )

    /**
     */
    override fun onActivityResult(request: Int, result: Int, data: Intent?) {

        if (AcmeStore.activitySucceeded(request, result, AcmeStore.REQUEST_LOGIN)) {
            initializeView()
        }
    }

    /**
     */
    private fun initializeView() {

        viewpager.apply {

            setPagingEnabled(true)

            adapter = BottomNavigationAdapter(supportFragmentManager).apply {
                addFragments(ShoppingCartFragment())
                addFragments(ProductCatalogFragment())
                addFragments(OrderHistoryFragment())
                addFragments(CustomerProfileFragment())
            }
        }

        main_bottomNavigation.apply {

            currentItem = 0
            isColored = true
            defaultBackgroundColor = Color.WHITE
            isTranslucentNavigationEnabled = false
            accentColor = fetchColor(R.color.bottomtab_0)
            inactiveColor = fetchColor(R.color.bottomtab_item_resting)
            setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomtab_item_resting))
            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_cart,
                R.drawable.ic_shopping_cart_24dp,
                R.color.colorPrimary
            ))

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_catalog,
                R.drawable.ic_catalog_24dp,
                R.color.colorPrimaryDark
            ))

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_history,
                R.drawable.ic_access_time_24dp,
                R.color.colorPrimary
            ))

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_profile,
                R.drawable.ic_person_24dp,
                R.color.colorPrimaryDark
            ))

            setOnTabSelectedListener { pos, selected ->
                onChooseTab(pos, selected)
            }
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        acmeInstance = application as AcmeStore

        Intent(applicationContext, LoginActivity::class.java).let {
            startActivityForResult(it, AcmeStore.REQUEST_LOGIN)
        }
    }

    /**
     */
    private fun onChooseTab(position: Int, wasSelected: Boolean): Boolean {

        if (wasSelected) {
            return true
        } else {
            supportActionBar?.setTitle(barTitles[position])
            viewpager.currentItem = position
        }

        return true
    }

    /**
     */
    private fun fetchColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)
}