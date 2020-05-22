package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterAddress
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBAddress
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.model.AddressData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_manage_address.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class ManageAddress : AppCompatActivity() {
    var mList = AddressData()
    var textViewCartCount: TextView? = null
    lateinit var adapter: AdapterAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_address)

        init()
    }

    private fun init() {
        this.setupToolbar("Address")
        getAddressData()
        adapter = AdapterAddress(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        button_add_address.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

    }

    fun getAddressData(){
        var mSession = SessionManager()
        var user = mSession.getUser()
        var requestQueue = Volley.newRequestQueue(this)
        var db = DBAddress()

        var request = StringRequest(Request.Method.GET, Endpoint.getAddress() + user.id,
            Response.Listener { response ->
                this.log(response.toString())
                var gson = GsonBuilder().create()
                mList = gson.fromJson(response.toString(), AddressData::class.java)

                adapter.setData(mList)
            },
            Response.ErrorListener {  response ->
                this.log(response.message.toString())

            })
        requestQueue.add(request)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
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
        getAddressData()
        updateCartCount()
        super.onStart()
    }

    override fun onResume() {
        getAddressData()
        super.onResume()
    }
}
