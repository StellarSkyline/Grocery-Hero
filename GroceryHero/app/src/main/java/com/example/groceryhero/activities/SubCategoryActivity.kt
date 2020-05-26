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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterFragment
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.Linker
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.model.Data
import com.example.groceryhero.model.SubCategory
import com.example.groceryhero.model.SubCategoryList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*

class SubCategoryActivity : AppCompatActivity(), Linker {

    lateinit var item: Data
    var mList:ArrayList<SubCategory> = ArrayList()
    var adapter = AdapterFragment(supportFragmentManager)
    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        item = intent.getSerializableExtra("data") as Data

        init()
    }

    private fun init() {
        getSubCategory()
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

        this.setupToolbar(item.catName)
    }

    fun getSubCategory() {
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET,Endpoint.getSubCategory() + item.catId,
            Response.Listener { response ->
                this.log(response.toString())
                var gson = GsonBuilder().create()

                var subCategoryList = gson.fromJson(response.toString(),SubCategoryList::class.java)
                mList = subCategoryList.data

                for(i in 0 until mList.size) {
                    adapter.addFragment(mList[i].subId, mList[i].subName)
                }
                adapter.setData(mList)


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

    override fun updateUI() {
        updateCartCount()
    }


}
