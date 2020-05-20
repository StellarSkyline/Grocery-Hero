package com.example.groceryhero.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.example.groceryhero.R
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.show
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.Users
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.input_layout_email
import kotlinx.android.synthetic.main.activity_register.*

class DeliveryActivity : AppCompatActivity() {
    var address = ""
    var mobile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        init()
    }

    private fun init() {

        this.setupToolbar("Delivery Details")

        setTextListener()

        button_submit.setOnClickListener {
            if(address.isEmpty()) {
                input_layout_address.error = "Address Required"
            } else if(mobile.isEmpty()) {
                input_layout_mobile_delivery.error = "Mobile Required"
            } else if(address.isNotEmpty() && mobile.isNotEmpty()) {
                var db = DBHelper()
                db.deleteTable()
                var intent = Intent(this,ThanksActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

            }
        }

    }

    fun setTextListener() {


        edit_text_address.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_address.error = "Address is Required"}
                else {address = edit_text_address.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_address.error = "Address is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_address.isErrorEnabled = false}
            }
        })


        edit_text_mobile_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_mobile_delivery.error = "Password is Required"}
                else {mobile = edit_text_mobile_delivery.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_mobile_delivery.error = "Password is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_mobile_delivery.isErrorEnabled = false}
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
        }
        return true
    }

}
