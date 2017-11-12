package org.marques999.acme.store

import kotlinx.android.synthetic.main.activity_main.*

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import org.marques999.acme.store.views.catalog.ProductCatalogFragment

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem

import org.marques999.acme.store.views.BottomNavigationAdapter
import org.marques999.acme.store.views.cart.ShoppingCartFragment
import org.marques999.acme.store.views.order.OrderHistoryFragment
import org.marques999.acme.store.views.profile.CustomerProfileFragment

class MainActivity : AppCompatActivity() {

    /**
     */
    private val titles = arrayOf(
        R.string.actionBar_cart,
        R.string.actionBar_catalog,
        R.string.actionBar_history,
        R.string.actionBar_profile
    )

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager.adapter = BottomNavigationAdapter(supportFragmentManager).apply {
            addFragments(ShoppingCartFragment())
            addFragments(ProductCatalogFragment())
            addFragments(OrderHistoryFragment())
            addFragments(CustomerProfileFragment())
        }

        main_bottomNavigation.let {

            it.currentItem = 0
            it.isColored = true
            it.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            it.accentColor = AcmeStore.fetchColor(this, R.color.colorAccent)

            it.setColoredModeColors(
                AcmeStore.fetchColor(this, R.color.colorAccent),
                AcmeStore.fetchColor(this, R.color.colorPrimaryDarker)
            )

            it.addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_cart,
                R.drawable.ic_cart_24dp,
                R.color.colorPrimaryDark
            ))

            it.addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_catalog,
                R.drawable.ic_catalog_24dp,
                R.color.colorPrimaryDark
            ))

            it.addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_history,
                R.drawable.ic_history_24dp,
                R.color.colorPrimaryDark
            ))

            it.addItem(AHBottomNavigationItem(
                R.string.bottomNavigation_profile,
                R.drawable.ic_person_24dp,
                R.color.colorPrimaryDark
            ))

            it.setOnTabSelectedListener { position, selected ->

                if (!selected) {
                    supportActionBar?.setTitle(titles[position])
                    viewpager.currentItem = position
                }

                true
            }
        }
    }
}