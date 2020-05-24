package com.example.groceryhero.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.log
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        init()
    }

    private fun init() {
        button_search.setOnClickListener{
            var search = edit_text_search.text.toString()
            getSearch(search)
        }

    }

    fun getSearch(searchItem:String) {
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET,Endpoint.getSearch()+searchItem,
            Response.Listener { response ->
                this.log(response.toString())

            },
            Response.ErrorListener { response ->
                this.log(response.message.toString())

            })
        requestQueue.add(request)
    }
}
