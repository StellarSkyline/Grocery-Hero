package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterOrderDetail
import com.example.groceryhero.adapter.AdapterOrderHistory
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.setupToolbar
import getData
import getProduct
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class OrderDetailActivity : AppCompatActivity() {
    var mList:ArrayList<getProduct> = ArrayList()
    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        init()
    }

    private fun init() {
        var item = intent.getSerializableExtra("item") as getData
        var num = intent.getIntExtra("number", 0)
        this.setupToolbar("Order: $num")


        for(i in 0 until item.products.size) {
            mList.add(item.products[i])
        }

        text_view_order_number.text = "Order Number: $num"
        text_view_total_price.text = "Total Order Price: ₹${item.orderSummary.ourPrice}"
        text_view_total_amount.text = "Total Number of Items: ${item.orderSummary.orderAmount}"
        text_view_order_address.text = "Shipped to: ${item.shippingAddress.houseNo} ${item.shippingAddress.streetName} ${item.shippingAddress.city}"

        var adapter = AdapterOrderDetail(this, mList)


        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapter
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

