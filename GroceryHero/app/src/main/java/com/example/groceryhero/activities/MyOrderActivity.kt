package com.example.groceryhero.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.SessionManager
import com.example.groceryhero.helper.log

class MyOrderActivity : AppCompatActivity() {
    val id = SessionManager().getId()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        init()

    }

    private fun init() {
        getOrder()

    }


    fun getOrder() {
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET, Endpoint.getOrder()+id,
            Response.Listener {response ->
                this.log(response.toString())
            },
            Response.ErrorListener {response ->
                this.log(response.message.toString())
            })

        requestQueue.add(request)
    }
}
