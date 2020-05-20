package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterFragment
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.model.Data
import com.example.groceryhero.model.SubCategory
import com.example.groceryhero.model.SubCategoryList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    lateinit var item: Data
    var mList:ArrayList<SubCategory> = ArrayList()
    var adapter = AdapterFragment(supportFragmentManager)

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
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



}
