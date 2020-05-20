package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.*
import com.example.groceryhero.model.Users
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    var name:String = ""
    var email:String = ""
    var mobile:String = ""
    var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {
        this.setupToolbar("Register")
        progress_bar.hide()
        setTextListener()

        button_register.setOnClickListener {
//            if(name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty())
//            {
//                this.toast("Register Failed")
//            } else {
//                var user = Users(name, email, password, mobile)
//                registerUser(user)
//            }

            if(name.isEmpty()) {
                input_layout_name.error = "Name is Required"
            } else if (email.isEmpty()) {
                input_layout_email.error = "Email is Required"
            } else if(mobile.isEmpty()) {
                input_layout_mobile.error = "Mobile is Required"
            } else if(password.isEmpty()) {
                input_layout_password.error = "Password is Required"
            } else if(name.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty() && password.isNotEmpty()) {
                progress_bar.show()
                var user = Users(name, email, password, mobile)
                registerUser(user)
            }
        }
    }

    fun registerUser(user: Users) {
        //Create Request Queue
        var requestQueue = Volley.newRequestQueue(this)

        //create Hashmap of saved value pair
        var params = HashMap<String, String>()
        params["firstName"] = user.name
        params["email"] = user.email
        params["password"] = user.password
        params["mobile"] = user.mobile

        //typecast params into jsonObject
        var jsonObject = JSONObject(params as Map<*, *>)


        //Pass json object to request
        var request = JsonObjectRequest(
            Request.Method.POST, Endpoint.getRegister(), jsonObject,
            Response.Listener { response ->
                this.log(response.toString())
                this.toast("Register Successful")

                //jump to new activity
                progress_bar.hide()
                startActivity(Intent(this, LoginActivity::class.java))

            },
            Response.ErrorListener { response ->
                this.log(response.message.toString())
                this.toast("Register Failed ${response.toString()}")
            })

        //add request to request queue to execute network call
        requestQueue.add(request)


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

        edit_text_email.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_email.error = "Email is Required"}
                else {email = edit_text_email.text.toString().trim()}

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_email.error = "Email is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_email.isErrorEnabled = false}
            }
        })

        edit_text_mobile.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_mobile.error = "Mobile is Required"}
                else {mobile = edit_text_mobile.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_mobile.error = "Mobile is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_mobile.isErrorEnabled = false}
            }
        })

        edit_text_password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_password.error = "Password is Required"}
                else {password = edit_text_password.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_password.error = "Password is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_password.isErrorEnabled = false}
            }
        })

    }
}
