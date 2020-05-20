package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterCart
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.fragments.HomeFragment
import com.example.groceryhero.fragments.ProfileFragment
import com.example.groceryhero.helper.*
import com.example.groceryhero.model.ProductsDB
import kotlinx.android.synthetic.main.activity_cart.*
class CartActivity : AppCompatActivity(), View.OnClickListener, Linker {

    var mList:ArrayList<ProductsDB> = ArrayList()
    var total:Double = 0.0
    var totalQty:Int = 0
    lateinit var db:DBHelper
    lateinit var adapter:AdapterCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
    }

    private fun init() {
        this.setupToolbar("Cart")
        db = DBHelper()
        mList = db.readData()
        adapter = AdapterCart(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        updateUI()
        button_delete.setOnClickListener(this)
        button_empty_back.setOnClickListener(this)
        button_checkout.setOnClickListener(this)
    }



    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_delete -> {
                mList.clear()
                db.deleteTable()
                updateUI()
            }
            R.id.button_empty_back -> {
                finish()
            }
            R.id.button_checkout -> {
                startActivity(Intent(this, OrderSummary::class.java))
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
        }
        return true
    }

    override fun updateUI() {
        total = 0.0
        totalQty = 0
        mList = db.readData()
        adapter.setData(mList)

        if(mList.size != 0) {
            for(i in 0 until mList.size) {
                total += (mList[i].price.toDouble() * mList[i].quantity.toDouble())
                totalQty += (mList[i].quantity.toInt())
                view_empty.hide()

            }
        } else {
            view_empty.show()
        }
        text_view_cart_items.text = "Total items in cart: ${totalQty}"
        text_view_cart_price.text = "Total Price: ₹${total}"
    }


}
