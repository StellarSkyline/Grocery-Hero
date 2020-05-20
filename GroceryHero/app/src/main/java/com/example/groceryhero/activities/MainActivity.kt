package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterSlider
import com.example.groceryhero.fragments.HomeFragment
import com.example.groceryhero.fragments.ProfileFragment
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.setupToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var handler = Handler()
    var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        var mList:ArrayList<Int> = ArrayList()
        mList.add(R.drawable.img_slider1)
        mList.add(R.drawable.img_slider2)
        mList.add(R.drawable.img_slider3)

        var adapterSlider = AdapterSlider(this, mList)
        view_pager_slider.adapter = adapterSlider

        loadFragment(HomeFragment())
        this.setupToolbar("Grocery Hero")
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        var session = SessionManager()
        var name = session.getName()

        text_view_welcome.text = "Welcome $name"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment:Fragment
        return when(item.itemId) {
            R.id.navigation_home -> {
                fragment = HomeFragment()
                loadFragment(fragment)
                return true}
            R.id.navigation_search -> {
                //add fragment and functionality
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







}
