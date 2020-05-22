package com.example.groceryhero.activities

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.example.groceryhero.R
import com.example.groceryhero.database.DBAddress
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.show
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.AddData
import com.example.groceryhero.model.Users
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.input_layout_email
import kotlinx.android.synthetic.main.activity_register.*

class DeliveryActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var db:DBAddress
    var item:ArrayList<AddData> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        init()
    }

    private fun init() {
        button_edit_address.setOnClickListener(this)
        button_submit.setOnClickListener(this)

        db = DBAddress()
        item = db.readData()
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
                startActivity(Intent(this, ThanksActivity::class.java))
            }
            R.id.button_edit_address -> {
                startActivity(Intent(this, ManageAddress::class.java))
            }
        }
    }

    fun updateUI() {
        db = DBAddress()
        item = db.readData()
        if(db.isPopulated()) {
            text_view_address.text = "${item[0].houseNo} ${item[0].streetName} ${item[0].type} "
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

}
