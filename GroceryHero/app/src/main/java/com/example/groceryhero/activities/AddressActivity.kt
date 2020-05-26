package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.Address
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*
import org.json.JSONObject

class AddressActivity : AppCompatActivity() {
    var mySession = SessionManager()
    var textViewCartCount: TextView? = null
    var houseNo = ""
    var streetName = ""
    var country =""
    var city = ""
    var zipcode = ""
    var type =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        init()
    }

    private fun init() {
        this.setupToolbar("Add Address")
        mySession = SessionManager()
        var user = mySession.getUser()
        setTextListener()

        button_address_add.setOnClickListener {

            if(houseNo.isEmpty()) {
                input_layout_house_number.error = "House Number Required"
            } else if( streetName.isEmpty()) {
                input_layout_street_name.error = "Street Name is Required"
            } else if(country.isEmpty()) {
                input_layout_country_address.error = "Country is Required"
            } else if(zipcode.isEmpty()) {
                input_layout_zip_address.error = "Zipcode is Required"
            }  else if(city.isEmpty()) {
                input_layout_city_address.error = "City is Required"
            }else if(type.isEmpty()) {
                input_layout_type_address.error = "Type is Required"
            } else {
                var mAddress = Address(houseNo = houseNo, streetName = streetName, country = country,
                    zipcode = zipcode, city = city, type = type)
                putAddress(mAddress)
            }
        }

    }

    fun putAddress(mAddress:Address) {

        var requestQueue = Volley.newRequestQueue(this)
        var params = HashMap<String, String>()
        params["name"] = mAddress.name
        params["houseNo"] = mAddress.houseNo
        params["mobile"] = mAddress.mobile
        params["streetName"] = mAddress.streetName
        params["location"] = mAddress.country
        params["city"] = mAddress.city
        params["pincode"] = mAddress.zipcode
        params["userId"] = mAddress.userId
        params["type"] = mAddress.type

        var jsonObject = JSONObject(params as Map<*, *>)

        var request = JsonObjectRequest(Request.Method.POST, Endpoint.addAddress(), jsonObject,
            Response.Listener{response ->
                this.toast(response.get("message").toString())

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

    fun setTextListener() {
        edit_text_house_number.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_house_number.error = "House Number is Required"}
                else {houseNo = edit_text_house_number.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_house_number.error = "House Number is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_house_number.isErrorEnabled = false}
            }
        })


        edit_text_street_name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_street_name.error = "Street Name is Required"}
                else {streetName = edit_text_street_name.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_street_name.error = "Street Name is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_street_name.isErrorEnabled = false}
            }
        })

        edit_text_country_address.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_country_address.error = "Country is Required"}
                else {country = edit_text_country_address.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_country_address.error = "Country is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_country_address.isErrorEnabled = false}
            }
        })

        edit_text_zip_address.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_zip_address.error = "Zipcode is Required"}
                else {zipcode = edit_text_zip_address.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_zip_address.error = "Zipcode is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_zip_address.isErrorEnabled = false}
            }
        })

        edit_text_city_address.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_city_address.error = "City is Required"}
                else {city = edit_text_city_address.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_city_address.error = "City is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_city_address.isErrorEnabled = false}
            }
        })

        edit_text_type_address.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_city_address.error = "Type is Required"}
                else { type = edit_text_type_address.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_type_address.error = "Type is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_type_address.isErrorEnabled = false}
            }
        })

    }

}
