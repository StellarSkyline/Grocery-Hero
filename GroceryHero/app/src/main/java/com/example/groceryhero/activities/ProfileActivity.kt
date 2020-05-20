package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.example.groceryhero.R
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.setupToolbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_email
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_name
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class ProfileActivity : AppCompatActivity() {

    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    private fun init() {
        this.setupToolbar("Profile")
        var session = SessionManager()

        var user = session.getUser()

        text_view_name.text = "Name: ${user.name}"
        text_view_email.text = "Email: ${user.email}"
        text_view_mobile.text = "Mobile: ${user.mobile}"

        button_logout.setOnClickListener{
            session.logout()
            var db = DBHelper()
            db.deleteTable()
            startActivity(Intent(this, StartActivity::class.java))

        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
        }
        return true
    }

    fun updateCartCount() {
        var db = DBHelper()
        var items = db.getTotalQuantity()
        if(items == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = items.toString()
        }
    }

    override fun onStart() {
        updateCartCount()
        super.onStart()
    }

}
