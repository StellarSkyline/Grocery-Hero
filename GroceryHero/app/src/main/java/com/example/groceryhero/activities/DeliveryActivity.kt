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
    var firstName = ""
    var lastName = ""
    var street = ""
    var country = ""
    var zip = ""
    var city = ""
    var state = ""
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
            if(firstName.isEmpty()){input_layout_first_name_delivery.error = "First Name required"}
            else if (lastName.isEmpty()){input_layout_last_name_delivery.error = "Last Name Required"}
            else if (street.isEmpty()){input_layout_street_delivery.error = "Street Address is required"}
            else if (country.isEmpty()){input_layout_country_delivery.error = "Country is Required"}
            else if (zip.isEmpty()){input_layout_zip_delivery.error = "ZIP Code is Required"}
            else if (city.isEmpty()){input_layout_city_delivery.error = "City is Required"}
            else if(state.isEmpty()){input_layout_state_delivery.error = "State is Required"}
            else if(mobile.isEmpty()){input_layout_mobile_delivery.error = "Mobile is Required"}
            else {
                //Write code here if we want to save the information
                startActivity(Intent(this, ThanksActivity::class.java))
                finish()
            }

        }

    }

    fun setTextListener() {

        edit_text_first_name_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_first_name_delivery.error = "First Name is Required"}
                else {firstName = edit_text_first_name_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_first_name_delivery.error = "First Name is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_first_name_delivery.isErrorEnabled = false}
            }
        })

        edit_text_last_name_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_first_name_delivery.error = "Last Name is Required"}
                else {lastName = edit_text_last_name_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_last_name_delivery.error = "Street Address is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_last_name_delivery.isErrorEnabled = false}
            }
        })

        edit_text_street_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_street_delivery.error = "Street Address is Required"}
                else {street = edit_text_street_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_street_delivery.error = "Street Address is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_street_delivery.isErrorEnabled = false}
            }
        })

        edit_text_country_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_country_delivery.error = "Country is Required"}
                else {country = edit_text_country_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_country_delivery.error = "Country is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_country_delivery.isErrorEnabled = false}
            }
        })

        edit_text_zip_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_zip_delivery.error = "ZIP Code is Required"}
                else {zip = edit_text_zip_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_zip_delivery.error = "ZIP Code is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_zip_delivery.isErrorEnabled = false}
            }
        })

        edit_text_city_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_city_delivery.error = "City is Required"}
                else {city = edit_text_city_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_city_delivery.error = "City is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_city_delivery.isErrorEnabled = false}
            }
        })

        edit_text_state_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_state_delivery.error = "State is Required"}
                else {state = edit_text_state_delivery.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_state_delivery.error = "State is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_state_delivery.isErrorEnabled = false}
            }
        })


        edit_text_mobile_delivery.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_mobile_delivery.error = "Mobile # is Required"}
                else {mobile = edit_text_mobile_delivery.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_mobile_delivery.error = "Mobile # is Required"}
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
