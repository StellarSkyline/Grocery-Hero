package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.groceryhero.R
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.setupToolbarNoBack
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        init()
    }

    private fun init() {
        this.setupToolbarNoBack("Login or Register")
        button_register.setOnClickListener(this)
        button_login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_register -> {startActivity((Intent(this, RegisterActivity::class.java)))}
            R.id.button_login -> {startActivity(Intent(this, LoginActivity::class.java))}
        }
    }

}
