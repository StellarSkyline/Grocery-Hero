package com.example.groceryhero.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    val CHANNEL_ID = "Order Details"
    val NOTIFICATION_ID = 1



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
        //create Order request

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
                setData()
                postOrder()
                createActionButtonNotification()
                dbC.deleteData()
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

        checkOutOrder = Orders(userId = mUser.id,user=mUser,shippingAddress = shippingAdd,products = mProducts,orderSummary = summary)

    }

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var name = "Order Details"
            var description = "Cart and Order Details"
            var importance = NotificationManager.IMPORTANCE_DEFAULT
            var channel = NotificationChannel(CHANNEL_ID,name,importance)
            channel.description = description

            var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun createActionButtonNotification() {
        createNotificationChannel()

        var intent = Intent(this, MyOrderActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        var pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)
        var bitmap = BitmapFactory.decodeResource(resources,R.drawable.icon_logo)


        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder
            .setStyle(NotificationCompat.BigTextStyle().bigText("Price: ${dbC.calculateOrder().totalPrice} | Quantity: ${dbC.calculateOrder().totalQuantity} | Address: ${dbA.readData()[0].houseNo} ${dbA.readData()[0].streetName}"))
            .setSmallIcon(R.drawable.ic_baseline_shopping_cart_24)
            .setLargeIcon(bitmap)
            .setContentTitle("Order Is On the way")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var manager = NotificationManagerCompat.from(this)
        manager.notify(NOTIFICATION_ID, builder.build())

    }

}
