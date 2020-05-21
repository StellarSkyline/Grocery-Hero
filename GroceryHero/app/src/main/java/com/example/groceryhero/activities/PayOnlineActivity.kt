package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.example.groceryhero.R
import com.example.groceryhero.helper.setupToolbar
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.activity_pay_online.*

class PayOnlineActivity : AppCompatActivity() {
    var name = ""
    var number = ""
    var expire = ""
    var security = ""
    var zip = ""
    var item = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_online)

        init()
    }

    private fun init() {
        item = intent.getDoubleExtra("total", 0.0)
        button_pay.text = "Pay: ₹${item}"
        this.setupToolbar("Pay Online")
        setTextListener()

        button_pay.setOnClickListener {
            if(name.isEmpty()) {
                input_layout_name.error = "Name is Required"
            } else if(number.isEmpty()) {
                input_layout_number.error = "Card Number is Required"
            } else if(expire.isEmpty()) {
                input_layout_expire.error = "Expiration Date is Required"
            } else if(security.isEmpty()) {
                input_layout_security.error = "Security Code is Required"
            } else if(zip.isEmpty()) {
                input_layout_zip.error = "ZIP Code Required"
            } else {
                startActivity(Intent(this, DeliveryActivity::class.java))
            }
        }

    }

    fun setTextListener() {

        edit_text_name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_name.error = "Name is Required"}
                else {name = edit_text_name.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_name.error = "Name is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_name.isErrorEnabled = false}
            }
        })

        edit_text_number.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_number.error = "Card Number is Required"}
                else {number = edit_text_number.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_number.error = "Card Number is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_number.isErrorEnabled = false}
            }
        })

        edit_text_expire.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_expire.error = "Expiration Date is Required"}
                else {expire = edit_text_expire.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_expire.error = "Expiration Date is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_expire.isErrorEnabled = false}
            }
        })

        edit_text_security.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_security.error = "Security Number is Required"}
                else {security = edit_text_security.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_security.error = "Security Number is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_security.isErrorEnabled = false}
            }
        })

        edit_text_zip.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_zip.error = "ZIP Code is Required"}
                else {zip = edit_text_zip.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_zip.error = "ZIP Code is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_zip.isErrorEnabled = false}
            }
        })


    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        button_pay.text = "Pay: ₹${item}"
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
        }
        return true
    }

}
