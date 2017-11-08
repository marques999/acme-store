package org.marques999.acme.store

import android.os.Bundle
import android.graphics.Color
import android.support.v7.app.AppCompatActivity

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.store.catalog.ProductCatalogFragment
import org.marques999.acme.store.customers.CustomerProfileFragment
import org.marques999.acme.store.history.OrderHistoryFragment
import org.marques999.acme.store.products.ShoppingCartFragment
import org.marques999.acme.store.view.BottomNavigationAdapter

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
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        acmeInstance = application as AcmeStore

        viewpager.adapter = BottomNavigationAdapter(supportFragmentManager).apply {
            addFragments(ShoppingCartFragment())
            addFragments(ProductCatalogFragment())
            addFragments(OrderHistoryFragment())
            addFragments(CustomerProfileFragment())
        }

        main_bottomNavigation.apply {

            currentItem = 0
            isColored = true
            defaultBackgroundColor = Color.WHITE
            isTranslucentNavigationEnabled = false
            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            accentColor = AcmeStore.fetchColor(this@MainActivity, R.color.colorAccent)
            inactiveColor = AcmeStore.fetchColor(this@MainActivity, R.color.colorPrimaryDarker)

            setColoredModeColors(
                AcmeStore.fetchColor(this@MainActivity, R.color.colorAccent),
                AcmeStore.fetchColor(this@MainActivity, R.color.colorPrimaryDarker)
            )

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_cart,
                R.drawable.ic_cart_24dp,
                R.color.colorPrimaryDark
            ))

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_catalog,
                R.drawable.ic_catalog_24dp,
                R.color.colorPrimaryDark
            ))

            addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_history,
                R.drawable.ic_history_24dp,
                R.color.colorPrimaryDark
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
    private fun onChooseTab(position: Int, wasSelected: Boolean): Boolean {

        if (wasSelected) {
            return true
        } else {
            supportActionBar?.setTitle(barTitles[position])
            viewpager.currentItem = position
        }

        return true
    }
}