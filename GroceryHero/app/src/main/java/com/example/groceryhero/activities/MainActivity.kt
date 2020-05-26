package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterSlider
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.fragments.HomeFragment
import com.example.groceryhero.fragments.ProfileFragment
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.setupToolbarNoBack
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var textViewCartCount: TextView? = null
    var imageViewOval: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        this.setupToolbarNoBack("Grocery Hero")
        var mList:ArrayList<Int> = ArrayList()
        mList.add(R.drawable.img_slider1)
        mList.add(R.drawable.img_slider2)
        mList.add(R.drawable.img_slider3)

        var adapterSlider = AdapterSlider(this, mList)
        slider_view.setSliderAdapter(adapterSlider)
        slider_view.startAutoCycle()
        slider_view.setIndicatorAnimation(IndicatorAnimations.WORM)
        slider_view.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        loadFragment(HomeFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        var session = SessionManager()
        var name = session.getName()

        text_view_welcome.text = "Welcome $name"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
            R.id.toolbar_home -> {startActivity(Intent(this, MainActivity::class.java))}
            R.id.toolbar_profile -> {startActivity(Intent(this, ProfileActivity::class.java))}
            R.id.toolbar_search -> {startActivity(Intent(this, SearchActivity::class.java))}
            R.id.toolbar_order_history ->{startActivity(Intent(this, MyOrderActivity::class.java))}
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        var item = menu.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_cart_badge)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_count_badge
        updateCartCount()


        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun updateCartCount() {
        var db = DBHelper()
        var items = db.getTotalQuantity()
        if(items == 0) {
            textViewCartCount?.visibility = View.GONE
            imageViewOval?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            imageViewOval?.visibility = View.VISIBLE
            textViewCartCount?.text = items.toString()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment:Fragment
        return when(item.itemId) {
            R.id.navigation_home -> {
                fragment = HomeFragment()
                loadFragment(fragment)
                return true}
            R.id.navigation_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true}
            R.id.navigation_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true }
            R.id.navigation_Profile -> {
                startActivity((Intent(this, ProfileActivity::class.java)))
                return true
            } else -> {return true}
        }
    }

    override fun onStart() {
        updateCartCount()
        super.onStart()
    }







}
