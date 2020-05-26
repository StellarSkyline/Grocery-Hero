package com.example.groceryhero.activities

import android.content.Context
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
import com.example.groceryhero.adapter.AdapterProducts
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.*
import com.example.groceryhero.model.ProductList
import com.example.groceryhero.model.Products
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_sub_category.view.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class SearchActivity : AppCompatActivity(),Linker {
    lateinit var adapter:AdapterProducts
    var mList:ArrayList<Products> = ArrayList()
    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        init()
    }

    private fun init() {
        this.setupToolbar("Search")
        adapter = AdapterProducts(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        button_search.setOnClickListener{
            var search = edit_text_search.text.toString()
            getSearch(search)
        }

    }

    fun getSearch(searchItem:String) {
        progress_bar.show()
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET,Endpoint.getSearch()+searchItem,
            Response.Listener { response ->
                this.log(response.toString())
                var gson = GsonBuilder().create()
                var productList: ProductList = gson.fromJson(response.toString(), ProductList::class.java)

                mList = productList.data

                adapter.setData(mList)
                progress_bar.hide()

            },
            Response.ErrorListener { response ->
                this.log(response.message.toString())

            })
        requestQueue.add(request)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
            R.id.toolbar_home -> {startActivity(Intent(this, MainActivity::class.java))}
            R.id.toolbar_profile -> {startActivity(Intent(this, ProfileActivity::class.java))}
            R.id.toolbar_search -> {startActivity(Intent(this, SearchActivity::class.java))}
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

    override fun onResume() {
        updateCartCount()
        super.onResume()
    }

    override fun updateUI() {
        updateCartCount()
    }
}
