package com.example.groceryhero.activities

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBAddress
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.*
import com.example.groceryhero.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.input_layout_email
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class DeliveryActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var dbA:DBAddress
    lateinit var dbC:DBHelper
    lateinit var mSession:SessionManager
    lateinit var mUser:Users
    lateinit var shippingAdd:ShippingAddress
    lateinit var summary:Summary
    lateinit var checkOutOrder:Orders
    var mAddress:ArrayList<AddData> = ArrayList()
    var mProducts:ArrayList<ProductsDB> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        init()
    }

    private fun init() {
        button_edit_address.setOnClickListener(this)
        button_submit.setOnClickListener(this)

        //intialize data
        dbA = DBAddress()
        dbC = DBHelper()
        mSession = SessionManager()

        //Call in function to set data
        setData()
        //create Order request
        checkOutOrder = Orders(userId = mUser.id,user=mUser,shippingAddress = shippingAdd,products = mProducts,orderSummary = summary)
        mAddress = dbA.readData()
        this.setupToolbar("Delivery Details")
        updateUI()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
        }
        return true
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_submit -> {
                postOrder()
                startActivity(Intent(this, ThanksActivity::class.java))
            }
            R.id.button_edit_address -> {
                startActivity(Intent(this, ManageAddress::class.java))
            }
        }
    }

    private fun updateUI() {
        dbA = DBAddress()
        mAddress = dbA.readData()
        if(dbA.isPopulated()) {
            text_view_address.text = "${mAddress[0].houseNo} ${mAddress[0].streetName} ${mAddress[0].type} "
        } else {
            text_view_address.text = "Set Primary Address"
        }
    }

    override fun onStart() {
        updateUI()
        super.onStart()
    }

    override fun onRestart() {
        updateUI()
        super.onRestart()
    }

    fun postOrder() {
        var requestQueue = Volley.newRequestQueue(this)
        var jsonObject = JSONObject(Gson().toJson(checkOutOrder))

        var request = JsonObjectRequest(Request.Method.POST, Endpoint.postOrder(), jsonObject,
            Response.Listener { response ->
                this.log(response.toString())
                this.toast(response.get("message").toString())

            },
            Response.ErrorListener {response ->
                this.log(response.message.toString())
            })
        requestQueue.add(request)




    }

    fun setData() {
        //set users
        mUser = mSession.getUser()

        //set address
        mAddress = dbA.readData()

        //set products
        mProducts = dbC.readData()

        //set passable address
        shippingAdd = ShippingAddress(houseNo = mAddress[0].houseNo, city = mAddress[0].city,streetName = mAddress[0].streetName, pincode = mAddress[0].pincode )

        //set passable summary
        summary = Summary(discount = dbC.calculateOrder().totalDiscount, ourPrice = dbC.calculateOrder().totalPrice, deliveryCharges = 300, orderAmount = dbC.getTotalQuantity())


    }

}
