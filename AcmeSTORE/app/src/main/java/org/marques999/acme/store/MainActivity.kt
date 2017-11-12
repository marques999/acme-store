package org.marques999.acme.store

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity



import android.view.Menu
import android.view.MenuItem

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.store.views.BottomNavigationAdapter
import org.marques999.acme.store.views.cart.ShoppingCartFragment
import org.marques999.acme.store.views.catalog.ProductCatalogFragment
import org.marques999.acme.store.views.catalog.ProductCatalogListener
import org.marques999.acme.store.views.order.OrderHistoryFragment
import org.marques999.acme.store.views.profile.CustomerProfileFragment

class MainActivity : AppCompatActivity(), ProductCatalogListener {

    /**
     */
    override fun onPurchase(product: String) {
        bottomNavigation.currentItem = 0
    }

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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     */
    private lateinit var bottomNavigation: AHBottomNavigation
    private lateinit var bottomNavigationAdapter: BottomNavigationAdapter

    /**
     */
    override fun onBackPressed() {

        AcmeDialogs.buildYesNo(this, R.string.main_confirmMessage,
            DialogInterface.OnClickListener { _, _ ->
                finish()
            }
        ).show()
    }

    /**
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.mainActivity_refresh -> {
            bottomNavigationAdapter.getFragment(bottomNavigation.currentItem).onRefresh()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = main_bottomNavigation
        bottomNavigationAdapter = BottomNavigationAdapter(supportFragmentManager)
        bottomNavigationAdapter.addFragments(ShoppingCartFragment())
        bottomNavigationAdapter.addFragments(ProductCatalogFragment())
        bottomNavigationAdapter.addFragments(OrderHistoryFragment())
        bottomNavigationAdapter.addFragments(CustomerProfileFragment())
        viewpager.adapter = bottomNavigationAdapter
        bottomNavigation.currentItem = 0
        bottomNavigation.isColored = true
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.accentColor = AcmeStore.fetchColor(this, R.color.colorAccent)

        bottomNavigation.setColoredModeColors(
            AcmeStore.fetchColor(this, R.color.colorAccent),
            AcmeStore.fetchColor(this, R.color.colorPrimaryDarker)
        )

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_cart,
            R.drawable.ic_cart_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_catalog,
            R.drawable.ic_catalog_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_history,
            R.drawable.ic_history_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.bottomNavigation_profile,
            R.drawable.ic_person_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.setOnTabSelectedListener { position, selected ->

            if (!selected) {
                supportActionBar?.setTitle(titles[position])
                viewpager.currentItem = position
            }

            true
        }
    }
}