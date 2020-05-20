package com.example.groceryhero.activities

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterCart
import com.example.groceryhero.adapter.AdapterOrder
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.ProductsDB
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.recycler_view
import kotlinx.android.synthetic.main.activity_order_summary.*
import kotlinx.android.synthetic.main.row_layout_order.*

class OrderSummary : AppCompatActivity(), View.OnClickListener {
    var mList:ArrayList<ProductsDB> = ArrayList()
    lateinit var db: DBHelper
    lateinit var adapter: AdapterOrder
    var couponCode = "HandsomeSeth"
    var enterCoupon = " "
    var localTotal = 0.0
    var deliveryFee = 300.0
    var checkDelivery:Boolean = true
    var clickOnce:Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)
        init()
    }

    private fun init() {
        this.setupToolbar("Order Summary")
        db = DBHelper()
        mList = db.readData()
        adapter = AdapterOrder(this)
        adapter.setData(mList)
        recycler_view_order.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_order.adapter = adapter
        button_coupon.setOnClickListener(this)
        button_pay_at_delivery.setOnClickListener(this)
        button_pay_online.setOnClickListener(this)
        updateUI()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
        }
        return true
    }

    private fun updateUI(){
        var data = db.calculateOrder()
        text_view_total.text = "Total price: ₹${data.totalPrice}"
        text_view_savings.text = "Total Savings: ₹${data.totalDiscount}"

        if(enterCoupon.isEmpty() || localTotal == 0.0) {
            localTotal = data.totalPrice
        }

        if(data.checkDeliver) {
            image_view_delivery.setImageResource(R.drawable.icon_delivery_free)
            deliveryFee = 0.0
            text_view_delivery.text = "Free Delivery"

        } else if(!data.checkDeliver && checkDelivery) {
            deliveryFee = 300.0
            text_view_delivery.text = "Delivery Charge: ₹${deliveryFee}"
            localTotal += deliveryFee
        }
        text_view_total_payment.text = "Total Payment: ₹${localTotal.toString()}"
        checkDelivery = false
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_coupon -> {
                if(clickOnce) {
                    enterCoupon = edit_text_coupon.text.toString()
                    if(enterCoupon == couponCode) {
                        this.toast("Coupon Discount Applied: ₹${localTotal * 0.30}")
                        localTotal -= (localTotal * 0.30)
                        clickOnce = false
                        updateUI()
                        text_view_total.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                    } else { updateUI()}
                }

            }
            R.id.button_pay_at_delivery -> {
                startActivity(Intent(this, DeliveryActivity::class.java))
                finish()
            }

            R.id.button_pay_online -> {
                var intent = Intent(this, PayOnlineActivity::class.java)
                intent.putExtra("total", localTotal)
                startActivity(intent)
            }
        }
    }

}
