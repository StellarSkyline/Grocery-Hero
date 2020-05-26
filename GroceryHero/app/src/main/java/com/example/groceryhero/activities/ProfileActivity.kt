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
import com.example.groceryhero.database.DBAddress
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.setupToolbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_email
import kotlinx.android.synthetic.main.fragment_profile.view.text_view_name
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    var textViewCartCount: TextView? = null
    var session = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    private fun init() {
        this.setupToolbar("Profile")
        session = SessionManager()

        var user = session.getUser()

        text_view_name.text = "Name: ${user.name}"
        text_view_email.text = "Email: ${user.email}"
        text_view_mobile.text = "Mobile: ${user.mobile}"
        checkAddress()

        button_logout.setOnClickListener(this)
        button_edit_address.setOnClickListener(this)
        button_order_history.setOnClickListener(this)
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
            R.id.toolbar_home -> {startActivity(Intent(this, MainActivity::class.java))}
            R.id.toolbar_profile -> {startActivity(Intent(this, ProfileActivity::class.java))}
            R.id.toolbar_search -> {startActivity(Intent(this, SearchActivity::class.java))}
            R.id.toolbar_order_history ->{startActivity(Intent(this, MyOrderActivity::class.java))}
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
        checkAddress()
        super.onStart()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_logout -> {
                session.logout()
                var db = DBHelper()
                var dbAddress = DBAddress()
                dbAddress.deleteData()
                db.deleteData()
                startActivity(Intent(this, StartActivity::class.java))
            }
            R.id.button_edit_address -> {
                startActivity(Intent(this, ManageAddress::class.java))
            }

            R.id.button_order_history -> {
                startActivity(Intent(this, MyOrderActivity::class.java))
            }

        }
    }

    fun checkAddress(){
        var db = DBAddress()
        var address = db.readData()

        if(db.isPopulated()) {
            text_view_address.text = "${address[0].houseNo} ${address[0].streetName} ${address[0].type} "
        } else {
            text_view_address.text = "Set primary Address"
        }
    }



}
