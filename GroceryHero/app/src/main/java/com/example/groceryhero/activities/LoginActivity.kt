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
import com.example.groceryhero.model.AuthResponse
import com.example.groceryhero.model.Users
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.input_layout_email
import kotlinx.android.synthetic.main.activity_login.progress_bar
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    var mySession = SessionManager()
    var email = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        this.setupToolbar("Login")
        progress_bar.hide()
        setTextListener()

        button_login.setOnClickListener {
            if(email.isEmpty()) {
                input_layout_email.error = "Email Required"
            } else if(password.isEmpty()) {
                input_layout_password_login.error = "Password Required"
            } else if(email.isNotEmpty() && password.isNotEmpty()) {
                progress_bar.show()
                //var user = Users(" ", email, password, " ", " ")
                loginUser(email, password)
            }

        }
    }

    fun loginUser(email:String, password:String) {

        //Create Request Queue
        var requestQueue = Volley.newRequestQueue(this)

        //create Hashmap of saved value pair
        var params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password


        //typecast params into jsonObject
        var jsonObject = JSONObject(params as Map<*, *>)


        //Pass json object to request
        var request = JsonObjectRequest(
            Request.Method.POST, Endpoint.getLogin(), jsonObject,
            Response.Listener { response ->
                this.log(response.toString())
                this.toast("Login Successful")

                //Create GSON Builder
                var gson = GsonBuilder().create()

                //save response into json by converting gson builder to auth response typecast into a java class
                var authResponse = gson.fromJson(response.toString(), AuthResponse::class.java)

                //save into shared pref file
                mySession.register(authResponse.user)


                mySession.saveToken(authResponse.token)

                //Change check to true
                mySession.login()

                progress_bar.hide()
                //jump to new activity
                startActivity(Intent(this, MainActivity::class.java))

            },
            Response.ErrorListener { response ->
                this.log(response.message.toString())
                this.toast("Register Failed ${response.message}")
                progress_bar.hide()
            })
        //add request to request queue to execute network call
        requestQueue.add(request)

    }

    fun setTextListener() {
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


        edit_text_password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()) {input_layout_password.error = "Password is Required"}
                else {password = edit_text_password.text.toString().trim()}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.isNullOrEmpty()) {input_layout_password_login.error = "Password is Required"}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotBlank()) {input_layout_password_login.isErrorEnabled = false}
            }
        })

    }
}
