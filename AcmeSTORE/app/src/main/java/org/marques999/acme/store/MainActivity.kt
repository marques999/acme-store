package org.marques999.acme.store

import android.view.Menu
import android.view.MenuItem

import org.marques999.acme.store.model.Order
import org.marques999.acme.store.model.Product

import android.os.Bundle
import android.content.DialogInterface
import android.support.v4.view.ViewPager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem

import kotlinx.android.synthetic.main.activity_main.*

import org.marques999.acme.store.views.BottomNavigationAdapter
import org.marques999.acme.store.views.cart.ShoppingCartFragment
import org.marques999.acme.store.views.customer.ProfileFragment
import org.marques999.acme.store.views.main.MainActivityListener
import org.marques999.acme.store.views.main.MainActivityMessage
import org.marques999.acme.store.views.order.OrderCheckoutDialog
import org.marques999.acme.store.views.order.OrderHistoryFragment
import org.marques999.acme.store.views.product.ProductCatalogFragment

class MainActivity : AppCompatActivity(), MainActivityListener {

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
    private lateinit var bottomNavigation: AHBottomNavigation
    private lateinit var bottomNavigationAdapter: BottomNavigationAdapter

    /**
     */
    override fun onUpdateBadge(view: Int, value: Int) {
        bottomNavigation.setNotification(value.toString(), view)
    }

    /**
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onNotify(messageId: MainActivityMessage, value: Any) {

        if (messageId == MainActivityMessage.PURCHASE && value is Product) {
            bottomNavigation.currentItem = BottomNavigationAdapter.CART
            (bottomNavigationAdapter.getFragment(
                BottomNavigationAdapter.CART
            ) as? ShoppingCartFragment)?.registerPurchase(value)
        } else if (messageId == MainActivityMessage.CHECKOUT && value is Order) {
            OrderCheckoutDialog.newInstance(value.token).show(supportFragmentManager, "ocd")
            bottomNavigation.currentItem = BottomNavigationAdapter.HISTORY
            (bottomNavigationAdapter.getFragment(
                BottomNavigationAdapter.HISTORY
            ) as? OrderHistoryFragment)?.registerOrder(value)
        }
    }

    /**
     */
    override fun onBackPressed() {
        AcmeDialogs.buildYesNo(this, R.string.main_logout,
            DialogInterface.OnClickListener { _, _ ->
                (application as AcmeStore).shoppingCart.checkout()
                finish() }
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
        bottomNavigationAdapter.addFragments(ProfileFragment())
        main_viewPager.adapter = bottomNavigationAdapter
        bottomNavigation.isColored = true
        bottomNavigation.isBehaviorTranslationEnabled = false
        bottomNavigation.isTranslucentNavigationEnabled = true
        bottomNavigation.currentItem = BottomNavigationAdapter.CART
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.accentColor = ContextCompat.getColor(this, R.color.colorAccent)

        bottomNavigation.setColoredModeColors(
            ContextCompat.getColor(this, R.color.colorAccent),
            ContextCompat.getColor(this, R.color.colorPrimaryDarker)
        )

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.navigation_cart,
            R.drawable.ic_cart_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.navigation_catalog,
            R.drawable.ic_catalog_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.navigation_history,
            R.drawable.ic_history_24dp,
            R.color.colorPrimaryDark
        ))

        bottomNavigation.addItem(AHBottomNavigationItem(
            R.string.navigation_profile,
            R.drawable.ic_person_24dp,
            R.color.colorPrimaryDark
        ))

        main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                bottomNavigation.currentItem = position
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) = Unit
        })

        bottomNavigation.setOnTabSelectedListener { position, selected ->

            if (!selected) {
                supportActionBar?.setTitle(titles[position])
                main_viewPager.currentItem = position
            }

            true
        }
    }
}